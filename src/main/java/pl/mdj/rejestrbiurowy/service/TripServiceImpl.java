package pl.mdj.rejestrbiurowy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.model.mappers.TripMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    Logger LOG = LoggerFactory.getLogger(TripService.class);

    TripRepository tripRepository;
    EmployeeRepository employeeRepository;
    CarRepository carRepository;
    TripMapper tripMapper;
    DayService dayService;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper, DayService dayService, EmployeeRepository employeeRepository, CarRepository carRepository) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.dayService = dayService;
        this.employeeRepository = employeeRepository;
        this.carRepository = carRepository;
    }

    @Override
    public List<TripDto> getAll() {
        return tripMapper.mapToDto(tripRepository.findByOrderByStartingDateDesc());
    }

    @Override
    public List<TripDto> getAllActive() {
        List<TripDto> activeTrips = getAll();
        return activeTrips.stream()
                .filter(trip -> !trip.getCancelled())
                .collect(Collectors.toList());
    }

    @Override
    public TripDto findById(Long id) throws CannotFindEntityException {
        Optional<Trip> optional = tripRepository.findById(id);
        if (optional.isPresent()) {
            return tripMapper.mapToDto(optional.get());
        } else {
            throw new CannotFindEntityException("Cannot find trip of id: " + id);
        }
    }

    @Override
    public void addOne(TripDto tripDto) throws EntityNotCompleteException, EntityConflictException, CannotFindEntityException {

        checkTripComplete(tripDto); // throws CFEE and ENCE
        Trip trip = tripMapper.mapToEntity(tripDto);  // Need to save Entity, not Dto
        checkAvailableCarConflict(trip); // throws EntityConflictException
        trip.setCreatedTime(LocalDateTime.now());
        trip.setLastModifiedTime(trip.getCreatedTime());
        tripRepository.save(trip);  // in this order to generate id before using save() inside DayService
        dayService.addTripToDay(trip);

    }

    /**
     * Should be used only after deleteByDto(), no checking performed
     *
     * @param tripDto
     */
    @Override
    public void cancelByDto(TripDto tripDto) {
        Trip trip = tripRepository.getOne(tripDto.getId());
        trip.setCancelled(true);
        trip.setCancelledTime(LocalDateTime.now());
        tripRepository.save(trip);
    }

    /**
     * @param tripDto
     * @throws CannotFindEntityException
     * @throws WrongInputDataException
     * @throws DataIntegrityViolationException
     */
    @Override
    public void deleteByDto(TripDto tripDto) throws CannotFindEntityException, WrongInputDataException, DataIntegrityViolationException {
        Optional<Trip> tripOptional = tripRepository.findById(tripDto.getId());
        if (!tripOptional.isPresent()) {
            throw new CannotFindEntityException("Rezerwacja nie istnieje, wystąpił błąd! (jednoczesna edycja z innego stanowiska)");
        }
        Employee requestedEmployee = tripOptional.get().getEmployee();
        if (!requestedEmployee.getPhoneNumber().equals(tripDto.getEmployee().getPhoneNumber())) {
            throw new WrongInputDataException("Niepoprawne dane, nie można anulować rezerwacji!");
        }
        throw new DataIntegrityViolationException("Usuwanie rezerwacji nie jest możliwe w tej wersji systemu!");
    }

    @Override
    public void update(TripDto tripDto) {
        tripRepository.findById(tripDto.getId()).ifPresent(trip -> {
            trip.setAdditionalMessage(tripDto.getAdditionalMessage());
            trip.setLastModifiedTime(LocalDateTime.now());
            tripRepository.save(trip);
        });
    }

    public List<TripDto> findAllByEmployee_Id(Long id) {
        List<Trip> tripList = tripRepository.findAllByEmployee_IdOrderByStartingDateAsc(id);
        return tripMapper.mapToDto(tripList);
    }

    public List<TripDto> findAllByCar_Id(Long id) {
        List<Trip> tripList = tripRepository.findAllByCar_IdOrderByStartingDateAsc(id);
        return tripMapper.mapToDto(tripList);
    }

    @Override
    public List<TripDto> findAllActiveByDate(LocalDate date) {
        Day day;
        try {
            day = dayService.findById(date);
        } catch (CannotFindEntityException e) {
            LOG.info(e.getMessage());
            return new ArrayList<>();
        }
        return tripMapper.mapToDto(day.getTrips().stream()
                .filter(trip -> !trip.getCancelled())
                .collect(Collectors.toList()));    }

    public List<TripDto> findAllByDate(LocalDate date) {
        Day day;
        try {
            day = dayService.findById(date);
        } catch (CannotFindEntityException e) {
            LOG.info(e.getMessage());
            return new ArrayList<>();
        }
        return tripMapper.mapToDto(day.getTrips());
    }

    private void checkAvailableCarConflict(Trip trip) throws EntityConflictException {

        Car car = trip.getCar();
        List<LocalDate> datesToCheck = dayService.getLocalDatesBetween(trip.getStartingDate(), trip.getEndingDate());
        List<LocalDate> unavailableDates = new ArrayList<>();
        List<Trip> existingTrips = new ArrayList<>();

        for (LocalDate date : datesToCheck) {
            try {
                existingTrips = dayService.findById(date).getTrips().stream()
                        .filter(t -> !t.getCancelled())
                        .collect(Collectors.toList());
            } catch (CannotFindEntityException ignored) {
            }

            for (Trip existingTrip :
                    existingTrips) {
                if (car == existingTrip.getCar()) {
                    unavailableDates.add(date);
                }
            }
        }

        if (!unavailableDates.isEmpty()) {
            throw new EntityConflictException("Rezerwacja nie powiodła się, pojazd jest już zajęty w dniach: " + unavailableDates.toString());
        }

    }

    private void checkTripComplete(TripDto tripDto) throws EntityNotCompleteException, CannotFindEntityException {
        if (tripDto.getCarId() == null) {
            throw new EntityNotCompleteException("Rezerwacja niemożliwa, nie ustawiono pojazdu!");
        }
        if (!carRepository.findById(tripDto.getCarId()).isPresent()) {
            throw new CannotFindEntityException("Rezerwacja niemożliwa, brak takiego pojazdu w bazie!");
        }
        if (tripDto.getEmployeeId() == null) {
            throw new EntityNotCompleteException("Rezerwacja niemożliwa, nie ustawiono pracownika!");
        }
        if (!employeeRepository.findById(tripDto.getEmployeeId()).isPresent()) {
            throw new CannotFindEntityException("Rezerwacja niemożliwa, brak takiego pracownika w bazie!");
        }
        if (tripDto.getStartingDate() == null) {
            throw new EntityNotCompleteException("Rezerwacja niemożliwa, nie ustawiono daty!");
        }
    }


}
