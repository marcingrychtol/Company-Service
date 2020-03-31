package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.DayRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DayServiceImpl implements DayService {

    DayRepository dayRepository;

    @Autowired
    public DayServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public List<Day> getAll() {
        return dayRepository.findAll();
    }

    @Override
    public Day findById(LocalDate id) throws CannotFindEntityException {

        Optional<Day> dayOptional = dayRepository.findById(id);
        if (dayOptional.isPresent()){
            return dayOptional.get();
        } else {
            throw new CannotFindEntityException("Cannot find day: " + id);
        }

    }

    @Override
    public Day addOne(Day day) throws EntityNotCompleteException, EntityConflictException {
        dayRepository.save(day);
        return day;
    }

    @Override
    public void deleteById(LocalDate id) {

    }

    @Override
    public boolean saveAll(List<Day> days) throws EntityNotCompleteException, EntityConflictException {
        for (Day day :
                days) {
            addOne(day);
        }
        return true;
    }

    @Override
    public void addTripToDay(Trip trip) throws EntityNotCompleteException, EntityConflictException {

        List<LocalDate> dates = new ArrayList<>();
        int i = 0;
        do {
            dates.add(trip.getStartingDate().plusDays(i)); // should work, because LocalDate is immutable
            i++;
        } while (trip.getStartingDate().plusDays(i).compareTo(trip.getEndingDate())<=0);

        for (LocalDate date :
                dates) {
            if(!dayRepository.existsById(date)){
                dayRepository.save(new Day(date));
            }
        }

        List<Day> days = dayRepository.findAllByIdBetweenOrderByIdAsc(trip.getStartingDate(), trip.getEndingDate());
        days.stream()
                .forEach(day -> day.getTrips().add(trip));
        saveAll(days);
    }
}
