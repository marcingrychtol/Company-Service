package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.entity.Day;
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
    public boolean addAll(List<Day> days) throws EntityNotCompleteException, EntityConflictException {
        for (Day day :
                days) {
            addOne(day);
        }
        return true;
    }

    @Override
    public List<Day> getDaysBetween(LocalDate startingDate, LocalDate endingDate) {

        List<LocalDate> dates = new ArrayList<>();
        int i = 0;
        do {
            dates.add(startingDate.plusDays(i)); // should work, because LocalDate is immutable
            i++;
        } while (startingDate.plusDays(i).compareTo(endingDate)<0);

        List<Day> days = new ArrayList<>();
        for (LocalDate date :
                dates) {
            days.add(dayRepository.findById(date).orElse(new Day()));
        }
        return days;
    }
}
