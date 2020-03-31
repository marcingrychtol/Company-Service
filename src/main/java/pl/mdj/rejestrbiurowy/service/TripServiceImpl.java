package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.model.mappers.TripMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TripServiceImpl implements TripService {

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
    public TripDto addOne(TripDto tripDto) throws EntityNotCompleteException, EntityConflictException {

        if (!tripDto.isComplete()){
            throw new EntityNotCompleteException("Rezerwacja niemożliwa, należy uzupełnić wszystkie wymagane parametry!");
        }

        Trip trip = tripMapper.mapToEntity(tripDto);  // Need to save Entity, not Dto

        checkConflict(trip); // throws exception

        trip.setCreatedTime(LocalDateTime.now());
        trip.setLastModifiedTime(trip.getCreatedTime());

        tripRepository.save(trip);
        dayService.addTripToDay(trip);

        return tripDto;

//        public void addUserToOrganisation(Long patientId, Long organisationId) {
//        Organisation organisation = organisations.findOne(organisationId);
//        User user = users.findOne(patientId);
//        organisation.getPatients().add(user);
//        organisations.save(organisation);
    }

    @Override
    public void deleteById(Long id) {
        tripRepository.deleteById(id);
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
        List<Trip> tripList = tripRepository.findAllByStartingDateEquals(date); // TODO: needs rebuild due to new Day algorithm
        return tripMapper.mapToDto(tripList);
    }

    private void checkConflict(Trip trip) throws EntityConflictException {         // TODO: check conflict musi sprawdzać w bazie dni
        ExampleMatcher tripMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("additionalMessage")
                .withIgnorePaths("employee")
                .withIgnorePaths("endingDate");

        Example<Trip> tripExample = Example.of(trip, tripMatcher);
        if (tripRepository.exists(tripExample)){
            throw new EntityConflictException("Rezerwacja nie powiodła się, pojazd jest już zajęty!");
        }
    }


}
