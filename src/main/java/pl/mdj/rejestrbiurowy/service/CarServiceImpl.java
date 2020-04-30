package pl.mdj.rejestrbiurowy.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.DayRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.model.mappers.CarMapper;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@NoArgsConstructor
public class CarServiceImpl implements CarService {

    private Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    CarRepository carRepository;
    CarMapper carMapper;
    TripRepository tripRepository;
    DayRepository dayRepository;
    DateMapper dateMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper, TripRepository tripRepository, DateMapper dateMapper, DayRepository dayRepository) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.tripRepository = tripRepository;
        this.dateMapper = dateMapper;
        this.dayRepository = dayRepository;
    }

    @Override
    public List<CarDto> findAll() {
        return carMapper.mapToDto(carRepository.findAllByOrderByBrand());    }

    @Override
    public List<CarDto> findAllActive() {
        return findAll().stream()
                .filter(car -> !car.isCancelled())
                .collect(Collectors.toList());
    }

    @Override
    public CarDto findById(Long id) throws CannotFindEntityException {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return carMapper.mapToDto(carOptional.get());
        } else {
            throw new CannotFindEntityException("Cannot find car of id: " + id);
        }
    }

    @Override
    public void addOne(CarDto inputDto) throws EntityConflictException, WrongInputDataException {
        Car entity = carMapper.mapToEntity(inputDto);

        checkInputLengthData(entity); // Throws WIDE
        checkDuplicates(entity); // Throws ECE
        carRepository.save(entity);
    }

    /**
     * @param carDto
     */
    @Override
    public void cancelByDto(CarDto carDto) throws CannotFindEntityException {
        Optional<Car> carOptional = carRepository.findById(carDto.getId());
        if (!carOptional.isPresent()) {
            throw new CannotFindEntityException("Wystąpił błąd, nie ma takiego pojazdu");
        }
        Car car = carOptional.get();
        car.setCancelled(true);
        carRepository.save(car);
    }

    /** Checking confirmation data.
     * Checking entity existence - uses lazy loading findById().
     * @param carDto
     * @throws WrongInputDataException
     * @throws CannotFindEntityException
     * @throws DataIntegrityViolationException
     */
    @Override
    public void deleteByDto(CarDto carDto) throws WrongInputDataException, CannotFindEntityException, DataIntegrityViolationException {
        Optional<Car> carOptional = carRepository.findById(carDto.getId());
        if (!carOptional.isPresent()) {
            throw new CannotFindEntityException("Wystąpił błąd, nie ma takiego pojazdu");
        }
        if (!carOptional.get().getRegistration().equals(carDto.getRegistration())) {
            throw new WrongInputDataException("Niepoprawne dane, nie można usunąć pojazdu!");
        }
        carRepository.deleteById(carDto.getId());
    }

    @Override
    public void enableByDto(CarDto carDto) throws CannotFindEntityException {

        Optional<Car> carOptional = carRepository.findById(carDto.getId());
        if (!carOptional.isPresent()) {
            throw new CannotFindEntityException("Wystąpił błąd, nie ma takiego pojazdu");
        }
        Car car = carOptional.get();
        car.setCancelled(false);
        carRepository.save(car);
    }

    /**
     *
     * @param carDto
     * @throws EntityConflictException
     * @throws WrongInputDataException
     * @throws CannotFindEntityException
     */
    @Override
    public void update(CarDto carDto) throws EntityConflictException, WrongInputDataException, CannotFindEntityException {

        Car car = carMapper.mapToEntity(carDto);

        checkInputLengthData(car); // WrongInputDataException
        checkDuplicates(car); // EntityConflictException

        Optional<Car> carOptional = carRepository.findById(carDto.getId());
        if (carOptional.isPresent()) {
            carOptional.get().setBrand(car.getBrand());
            carOptional.get().setModel(car.getModel());
            carOptional.get().setRegistration(car.getRegistration());
            carRepository.save(carOptional.get());
        } else {
            throw new CannotFindEntityException(
                    "Nie można wprowadzić danych, pojazd nie istnieje lub jest nieaktywny. " +
                            "Prawdopodobnie ktoś zmienił dane w międzyczasie");
        }

    }

    @Override
    public List<CarDto> getAvailableCarsByDay(LocalDate date) {

        List<CarDto> notAvailableCars = getNotAvailableCarsByDay(date);

        return carRepository.findAllByOrderByBrand().stream()
                .map(c -> carMapper.mapToDto(c))
                .filter(car -> !notAvailableCars.contains(car))
                .filter(car -> !car.isCancelled())
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getNotAvailableCarsByDay(LocalDate date) {
        Optional<Day> day = dayRepository.findById(date);

        return day.map(
                d -> d.getTrips().stream()
                        .filter(trip -> !trip.getCancelled())
                        .map(Trip::getCar)
                        .filter(car -> !car.getCancelled())
                        .map(car -> carMapper.mapToDto(car))
                        .collect(Collectors.toList())
        ).orElseGet(ArrayList::new);

    }

    @Override
    public void addPhoto(MultipartFile photo, Long id) throws CannotFindEntityException, WrongInputDataException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));

        try {
            if(fileName.contains("..")){
                throw new WrongInputDataException("Jakieś nieuznawalne znaki w nazwie pliku... " + fileName);
            }
            Optional<Car> carOptional = carRepository.findById(id);
            if (!carOptional.isPresent()) {
                throw new CannotFindEntityException("Brak takiego pojazdu w bazie, spróbuj ponownie");
            }
            Car car = carOptional.get();
            car.setImage(photo.getBytes());
            car.setImageName(fileName);
            car.setFileType(photo.getContentType());
            carRepository.save(car);
        } catch (IOException e) {
            LOG.error("Błąd odczytu bajtów z pliku!" + e.getMessage());
            throw new WrongInputDataException("Błąd odczytu bajtów z pliku! Nie wiem." + e.getMessage());
        }

    }

    private void checkInputLengthData(Car car) throws WrongInputDataException {
        if (
                car.getRegistration() == null
                        || car.getRegistration().length() < 5
                        || car.getBrand() == null
                        || car.getBrand().length() < 2
                        || car.getModel() == null
                        || car.getModel().length() < 2
        ) {
            throw new WrongInputDataException("Za krótkie dane, 5 znaków dla rejestracji, 2 znaki dla marki i modelu...");
        }
    }

    private void checkDuplicates(Car car) throws EntityConflictException {
        Optional<Car> carConflictTest = carRepository.findByRegistrationEquals(car.getRegistration());
        if (carConflictTest.isPresent() && !carConflictTest.get().getId().equals(car.getId())) {
            throw new EntityConflictException(
                    "Pojazd o rejestracji "
                            + car.getRegistration()
                            + " już istnieje! Jest to "
                            + carConflictTest.get().getBrand()
                            + " "
                            + carConflictTest.get().getModel()
            );
        }
    }

}
