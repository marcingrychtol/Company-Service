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
import pl.mdj.rejestrbiurowy.model.dto.*;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.model.mappers.TripMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
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
    DateMapper dateMapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper, DayService dayService, EmployeeRepository employeeRepository, CarRepository carRepository, DateMapper dateMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.dayService = dayService;
        this.employeeRepository = employeeRepository;
        this.carRepository = carRepository;
        this.dateMapper = dateMapper;
    }

    @Override
    public List<TripDto> findAll() {
        return tripMapper.mapToDto(tripRepository.findByOrderByStartingDateDesc());
    }

    @Override
    public List<TripDto> findAllActive() {
        List<TripDto> activeTrips = findAll();
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
    public Long addOne(TripDto tripDto) throws EntityNotCompleteException, EntityConflictException, CannotFindEntityException {
        checkTripComplete(tripDto); // throws CFEE and ENCE
        Trip trip = tripMapper.mapToEntity(tripDto);  // Need to save Entity, not Dto
        return addTrip(trip);
    }

    private Long addTrip(Trip trip) throws EntityConflictException, EntityNotCompleteException {
        checkAvailableCarConflict(trip); // throws EntityConflictException
        trip.setCreatedTime(LocalDateTime.now());
        trip.setLastModifiedTime(trip.getCreatedTime());
        if (trip.getAdditionalMessage() == null) {
            trip.setAdditionalMessage("");
        }
        tripRepository.save(trip);  // in this order to generate id before using save() inside DayService
        dayService.addTripToDay(trip);
        return trip.getId();
    }

    /**
     * @param tripDto
     */
    @Override
    public void cancelByDto(TripDto tripDto) throws CannotFindEntityException {
        Optional<Trip> tripOptional = tripRepository.findById(tripDto.getId());
        if (!tripOptional.isPresent()) {
            throw new CannotFindEntityException("Nie można znaleźć rezerwacji (jednoczesta edycja z innego stanowiska)!");
        }
        Trip trip = tripOptional.get();
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
    public void enableByDto(TripDto tripDto) throws CannotFindEntityException {
        Optional<Trip> tripOptional = tripRepository.findById(tripDto.getId());
        if (!tripOptional.isPresent()) {
            throw new CannotFindEntityException("Nie można znaleźć rezerwacji (jednoczesta edycja z innego stanowiska)!");
        }
        Trip trip = tripOptional.get();
        trip.setCancelled(false);
        tripRepository.save(trip);
    }

    @Override
    public void update(TripDto tripDto) {
        tripRepository.findById(tripDto.getId()).ifPresent(trip -> {
            trip.setEmployee(employeeRepository.getOne(tripDto.getEmployeeId()));
            trip.setAdditionalMessage(tripDto.getAdditionalMessage());
            trip.setLastModifiedTime(LocalDateTime.now());
            tripRepository.save(trip);
        });
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
                .collect(Collectors.toList()));
    }


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

    /**
     * Finds trips by any parameter provided, any field can be null
     *
     * @param filter TripDto object, any field can be null
     * @return
     */
    @Override
    public List<TripDto> findByFilter(TripDto filter) {

        LocalDate start;
        LocalDate end;
        boolean datesGiven = false;

        List<DayDto> days = new ArrayList<>();
        // at first let's find out if we have any dates provided, and get Days basing on that
        if (filter.getFilterStartingDate() != null) {
            start = dateMapper.toLocalDate(filter.getFilterStartingDate());
            datesGiven = true;
            if (filter.getFilterEndingDate() != null) {
                end = dateMapper.toLocalDate(filter.getFilterEndingDate());
                days = dayService.getDaysDtoBetween(start, end);
            } else {
                days = dayService.getDaysDtoAfter(start);
            }
        } else if (filter.getFilterEndingDate() != null) {
            end = dateMapper.toLocalDate(filter.getFilterEndingDate());
            days = dayService.getDaysDtoBefore(end);
            datesGiven = true;
        }

        if (!datesGiven) {
            return filterWhenNoDates(filter);
        }
        return filterWhenDatesGiven(days, filter);

    }

    private List<TripDto> filterWhenNoDates(TripDto filter) {
        List<TripDto> tripList = new ArrayList<>();
        boolean haveTouchedTripRepository = false;

        if (filter.getFilterCarId() != null) {
            tripList = tripMapper.mapToDto(
                    tripRepository.findAllByCar_IdOrderByStartingDateDesc(filter.getFilterCarId())
            );
            haveTouchedTripRepository = true;
        }

        if (filter.getFilterEmployeeId() != null) {
            if (haveTouchedTripRepository) {
                tripList = tripList.stream()
                        .filter(t -> t.getEmployeeId().equals(filter.getFilterEmployeeId()))
                        .collect(Collectors.toList());
            } else {
                tripList = tripMapper.mapToDto(tripRepository.findAllByEmployee_IdOrderByStartingDateDesc(filter.getFilterEmployeeId()));
                haveTouchedTripRepository = true;
            }
        }

        if (filter.getFilterAdditionalMessage() != null && !filter.getFilterAdditionalMessage().equals("")) {
            if (haveTouchedTripRepository) {
                tripList = tripList.stream()
                        .filter(t -> Pattern.compile(Pattern.quote(filter.getFilterAdditionalMessage()), Pattern.CASE_INSENSITIVE).matcher(t.getAdditionalMessage()).find())
                        .collect(Collectors.toList());
            } else {
                tripList = tripMapper.mapToDto(tripRepository.findAllByAdditionalMessageContainingIgnoreCase(filter.getFilterAdditionalMessage()));
            }
        }

        return tripList.stream()
                .filter(tripDto -> !tripDto.getCancelled())
                .collect(Collectors.toList());
    }

    private List<TripDto> filterWhenDatesGiven(List<DayDto> days, TripDto filter) {
        Set<TripDto> tripSet = new HashSet<>();
        List<TripDto> tripList;
        days.stream()
                .map(DayDto::getTrips)
                .forEach(tripSet::addAll);
        tripList = new ArrayList<>(tripSet);

        Comparator<TripDto> compareByStartingDate = Comparator.comparing(TripDto::getFilterStartingDate, Comparator.reverseOrder());
        tripList.sort(compareByStartingDate);

        if (filter.getFilterCarId() != null) {
            tripList = tripList.stream()
                    .filter(t -> t.getCarId().equals(filter.getFilterCarId()))
                    .collect(Collectors.toList());
        }
        if (filter.getFilterEmployeeId() != null) {
            tripList = tripList.stream()
                    .filter(t -> t.getEmployeeId().equals(filter.getFilterEmployeeId()))
                    .collect(Collectors.toList());
        }

        if (filter.getFilterAdditionalMessage() != null && !filter.getFilterAdditionalMessage().equals("")) {
            tripList = tripList.stream()
                    .filter(t -> Pattern.compile(Pattern.quote(filter.getFilterAdditionalMessage()), Pattern.CASE_INSENSITIVE).matcher(t.getAdditionalMessage()).find())
                    .collect(Collectors.toList());
        }
        return tripList.stream()
                .filter(tripDto -> !tripDto.getCancelled())
                .collect(Collectors.toList());
    }


    @Override
    public TripDto completeFilterDtoData(TripDto tripDto) {
        return tripMapper.completeTripData(tripDto);
    }

    @Override
    public List<TripDto> getTripsByCar(LocalDate startingDate, Car car, Integer scope) {
        return null;
    }

    /**
     * Used to find any trips conflicted with request
     * using findByFilter()
     * @param requestedTrips all trips from request joined together, must have car, and both dates
     * @return list of conflicted trips
     */
    @Override
    public List<TripDto> findConflictedTrips(List<TripDto> requestedTrips){
        return tripMapper.mapToDto(findConflicts(tripMapper.mapToEntity(requestedTrips)));
    }

    private List<Trip> findConflicts(List<Trip> requestedTrips){
        Set<Trip> existingTripSet = new HashSet<>();
        for (Trip trip : requestedTrips) {
            dayService.getDaysBetween(trip.getStartingDate(), trip.getEndingDate())
                    .stream()
                    .map(Day::getTrips)
                    .forEach(existingTripSet::addAll);
            existingTripSet = existingTripSet.stream()
                    .filter(t -> !t.getCancelled())
                    .filter(t -> t.getCar().getId().equals(trip.getCar().getId()))
                    .collect(Collectors.toSet());
        }
        return new ArrayList<>(existingTripSet);
    }

    @Override
    public List<TripDto> addAll(BookingParamsDto bookingParams) {

        List<TripDto> resolvedTripDtos = joinRequestedTrips(bookingParams);
        List<Trip> resolvedTrips = tripMapper.mapToEntity(resolvedTripDtos);
        Map<Trip, List<Trip>> conflictMap = new HashMap<>();
        for (Trip trip :
                resolvedTrips) {
            Set<Trip> existingTripSet = new HashSet<>();
            dayService.getDaysBetween(trip.getStartingDate(), trip.getEndingDate())
                    .stream()
                    .map(Day::getTrips)
                    .forEach(existingTripSet::addAll);
            existingTripSet = existingTripSet.stream()
                    .filter(t -> t.getCar().getId().equals(bookingParams.getCarId()))
                    .collect(Collectors.toSet());
            List<Trip> existingTripList = new ArrayList<>(existingTripSet);
            conflictMap.put(trip, existingTripList);
        }
        for (Trip newTrip :
                conflictMap.keySet()) {
            for (Trip existingTrip :
                    conflictMap.get(newTrip)) {
                removeConflicts(newTrip,existingTrip);
            }
        }
        List<Long> ids = new ArrayList<>();
        resolvedTripDtos.forEach(t -> {
            try {
                ids.add(addOne(t));
            } catch (EntityNotCompleteException | CannotFindEntityException e) {
                e.printStackTrace();
            } catch (EntityConflictException ignored) {
            }
        });

        resolvedTripDtos = new ArrayList<>();
        for (Long id :
                ids) {
            try {
                resolvedTripDtos.add(findById(id));
            } catch (CannotFindEntityException ignored) { }
        }
        return resolvedTripDtos;
    }

    private void checkAvailableCarConflict(Trip trip) throws EntityConflictException {

        Car car = trip.getCar();
        List<LocalDate> datesToCheck = dayService.getLocalDatesBetween(trip.getStartingDate(), trip.getEndingDate());

        List<Trip> existingTrips = getExistingTrips(datesToCheck);
        List<LocalDate> unavailableDates = getUnavailableDates(car, existingTrips, datesToCheck);

        if (!unavailableDates.isEmpty()) {
            throw new EntityConflictException("Rezerwacja nie powiodła się, pojazd jest już zajęty w dniach: " + unavailableDates.toString());
        }
    }

    private List<LocalDate> getUnavailableDates(Car car, List<Trip> existingTrips, List<LocalDate> datesToCheck) {
        List<LocalDate> unavailableDates = new ArrayList<>();
        for (LocalDate date : datesToCheck) {
            for (Trip existingTrip :
                    existingTrips) {
                if (car == existingTrip.getCar()) {
                    unavailableDates.add(date);
                }
            }
        }
        return unavailableDates;
    }

    private List<Trip> getExistingTrips(List<LocalDate> datesToCheck) {
        List<Trip> existingTrips = new ArrayList<>();
        for (LocalDate date : datesToCheck) {
            try {
                existingTrips = dayService.findById(date).getTrips().stream()
                        .filter(t -> !t.getCancelled())
                        .collect(Collectors.toList());
            } catch (CannotFindEntityException ignored) {
            }
        }
        return existingTrips;
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

    /**
     * Used to find all trips around request
     * Sets employee, car and additional message for all found trips
     *
     * @param bookingParams
     * @return
     */
    @Override
    public List<TripDto> joinRequestedTrips(BookingParamsDto bookingParams) {

        List<LocalDate> requestsDateList = new ArrayList<>();
        List<TripDto> requestedTripList = new ArrayList<>();

        for (CarDayInfoDto carDayInfoDto :
                bookingParams.getCarDayInfoList()) {
            if (carDayInfoDto.getRequested()) {
                LocalDate requestDate;
                requestDate = carDayInfoDto.getLDid();
                requestsDateList.add(requestDate);
            }
        }

        // FIRST case - no requests
        if (requestsDateList.isEmpty()) {
            return new ArrayList<>();
        }

        // SECOND case - one request
        if (requestsDateList.size() == 1) {
            TripDto trip = new TripDto();
            trip.setStartingDate(dateMapper.toDate(requestsDateList.get(0)));
            trip.setEndingDate(trip.getStartingDate());
            requestedTripList.add(trip);
        }


        LocalDate startingDate = requestsDateList.get(0);
        LocalDate endingDate = requestsDateList.get(0);
        boolean done = false;
        for (int i = 1; i < requestsDateList.size(); i++) {

            // first we check IS GAP?
            if (!requestsDateList.get(i).minusDays(1).equals(endingDate)) {
                TripDto trip = new TripDto();
                trip.setStartingDate(dateMapper.toDate(startingDate));
                trip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(trip);
                startingDate = requestsDateList.get(i);
                endingDate = requestsDateList.get(i);

            } else if (i == requestsDateList.size() - 1) {             // was this last iteration?
                TripDto trip = new TripDto();
                trip.setStartingDate(dateMapper.toDate(startingDate));
                endingDate = requestsDateList.get(i);
                trip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(trip);
                done = true;
            } else {
                endingDate = requestsDateList.get(i);
            }

            // was this last iteration and not done yet?
            if (i == requestsDateList.size() - 1 && !done) {
                TripDto lastOneTrip = new TripDto();
                lastOneTrip.setStartingDate(dateMapper.toDate(startingDate));
                lastOneTrip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(lastOneTrip);
            }
        }

        if (bookingParams.getAdditionalMessage() == null){
            bookingParams.setAdditionalMessage("");
        }

        for (TripDto trip :
                requestedTripList) {
            trip.setCarId(bookingParams.getCarId());
            trip.setEmployeeId(bookingParams.getEmployeeId());
            trip.setAdditionalMessage(bookingParams.getAdditionalMessage());
        }

        return requestedTripList;
    }

    /**
     * Used to make place in database.
     * Taking over only one existing trip in database. If more conflicts exists, must be called multiple times.
     * @param existingTrip must be trip connected to database context
     * @param requestedTrip any trip object with dates
     */
    private void removeConflicts(Trip requestedTrip, Trip existingTrip) {
        existingTrip.setCancelled(true);
        tripRepository.save(existingTrip);
        List<Trip> newTrips = new ArrayList<>();
        // TS after CS?
        if (existingTrip.getStartingDate().compareTo(requestedTrip.getStartingDate()) >= 0) {
            // TE after CE?
            if (existingTrip.getEndingDate().compareTo(requestedTrip.getEndingDate()) > 0) {
                Trip newTrip = new Trip();
                newTrip.setStartingDate(requestedTrip.getEndingDate().plusDays(1));
                newTrip.setEndingDate(existingTrip.getEndingDate());
                newTrips.add(newTrip);
            } else {
                return;
            }
        } else {
            if (existingTrip.getEndingDate().compareTo(requestedTrip.getEndingDate()) >= 0) {
                Trip newTrip1 = new Trip();
                newTrip1.setStartingDate(existingTrip.getStartingDate());
                newTrip1.setEndingDate(requestedTrip.getStartingDate().minusDays(1));
                Trip newTrip2 = new Trip();
                newTrip2.setStartingDate(requestedTrip.getEndingDate().plusDays(1));
                newTrip2.setEndingDate(existingTrip.getEndingDate());
                newTrips.addAll(Arrays.asList(newTrip1, newTrip2));
            } else {
                Trip newTrip = new Trip();
                newTrip.setStartingDate(existingTrip.getStartingDate());
                newTrip.setEndingDate(requestedTrip.getStartingDate().minusDays(1));
                newTrips.add(newTrip);
            }
        }

        newTrips.forEach(trip -> {
            trip.setCar(existingTrip.getCar());
            trip.setEmployee(existingTrip.getEmployee());
            try {
                addTrip(trip);
            } catch (EntityConflictException | EntityNotCompleteException ignored) { }
        });

    }


}
