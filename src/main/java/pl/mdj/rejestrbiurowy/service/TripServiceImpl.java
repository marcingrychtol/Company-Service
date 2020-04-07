package pl.mdj.rejestrbiurowy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.model.mappers.TripMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    Logger LOG = LoggerFactory.getLogger(TripService.class);

    TripRepository tripRepository;
    TripMapper tripMapper;
    DayService dayService;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper, DayService dayService) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.dayService = dayService;
    }

    @Override
    public List<TripDto> getAll() {
        return tripMapper.mapToDto(tripRepository.findByOrderByStartingDateAsc());
    }

    @Override
    public TripDto findById(Long id) throws CannotFindEntityException {
        Optional<Trip> optional = tripRepository.findById(id);
        if (optional.isPresent()){
            return tripMapper.mapToDto(optional.get());
        } else {
            throw new CannotFindEntityException("Cannot find employee of id: " + id);
        }
    }

    @Override
    public void addOne(TripDto tripDto) throws EntityNotCompleteException, EntityConflictException {

        if (!tripDto.isComplete()){
            throw new EntityNotCompleteException("Rezerwacja niemożliwa, należy uzupełnić wszystkie wymagane parametry!");
        }

        Trip trip = tripMapper.mapToEntity(tripDto);  // Need to save Entity, not Dto

        checkConflict(trip); // throws exception

        trip.setCreatedTime(LocalDateTime.now());
        trip.setLastModifiedTime(trip.getCreatedTime());

        tripRepository.save(trip);  // in this order to generate id before using save() inside DayService
        dayService.addTripToDay(trip);

    }

    @Override
    public void cancelByDto(TripDto tripDto) {
        tripRepository.findById(tripDto.getId()).ifPresent(trip -> {
            trip.setCancelled(true);
            trip.setCancelledTime(LocalDateTime.now());
            tripRepository.save(trip);
        });
    }

    @Override
    public void update(TripDto tripDto) {
        tripRepository.findById(tripDto.getId()).ifPresent(trip -> {
            trip.setAdditionalMessage(tripDto.getAdditionalMessage());
            tripRepository.save(trip);
        });
    }

    public List<TripDto> findAllByEmployee_Id(Long id){
        List<Trip> tripList = tripRepository.findAllByEmployee_IdOrderByStartingDateAsc(id);
        return tripMapper.mapToDto(tripList);
    }

    public List<TripDto> findAllByCar_Id(Long id){
        List<Trip> tripList = tripRepository.findAllByCar_IdOrderByStartingDateAsc(id);
        return tripMapper.mapToDto(tripList);
    }

    public List<TripDto> findAllByDate(LocalDate date){

        Day day;
        try {
            day = dayService.findById(date);
        } catch (CannotFindEntityException e) {
            LOG.info(e.getMessage());
            return new ArrayList<>();
        }
        return tripMapper.mapToDto(day.getTrips());
    }

    private void checkConflict(Trip trip) throws EntityConflictException {

        Car car = trip.getCar();
        List<LocalDate> datesToCheck = dayService.getLocalDatesBetween(trip.getStartingDate(), trip.getEndingDate());
        List<LocalDate> unavailableDates = new ArrayList<>();
        List<Trip> existingTrips = new ArrayList<>();


        for (LocalDate date :datesToCheck) {

            try {
                existingTrips = dayService.findById(date).getTrips();
            } catch (CannotFindEntityException e) { }

            for (Trip existingTrip :
                    existingTrips) {
                if (car == existingTrip.getCar()){ unavailableDates.add(date); }
            }
        }

        if (!unavailableDates.isEmpty()){
            throw new EntityConflictException("Rezerwacja nie powiodła się, pojazd jest już zajęty w dniach: " + unavailableDates.toString());
        }

    }


}
