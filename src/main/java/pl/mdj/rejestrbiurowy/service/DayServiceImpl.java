package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.model.mappers.DayMapper;
import pl.mdj.rejestrbiurowy.repository.DayRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DayServiceImpl implements DayService {

    DayRepository dayRepository;
    DayMapper dayMapper;

    @Autowired
    public DayServiceImpl(DayRepository dayRepository, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.dayMapper = dayMapper;
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

    private void saveOne(Day day) throws EntityNotCompleteException, EntityConflictException {
        dayRepository.save(day);
    }


    private void saveAll(List<Day> days) throws EntityNotCompleteException, EntityConflictException {
        for (Day day : days) {
            saveOne(day);
        }
    }

    @Override
    public void addTripToDay(Trip trip) throws EntityNotCompleteException, EntityConflictException {

        fillGapsWithinRequest(trip.getStartingDate(), trip.getEndingDate());
        List<Day> days = getDaysBetween(trip.getStartingDate(), trip.getEndingDate());
        days.forEach(day -> day.getTrips().add(trip));
        saveAll(days);
    }

    @Override
    public List<DayDto> getDaysDtoBetween(LocalDate start, LocalDate end) {
        return dayMapper.mapToDto(getDaysBetween(start, end));
    }

    @Override
    public List<LocalDate> getLocalDatesBetween(LocalDate start, LocalDate end){
        List<LocalDate> dates = new ArrayList<>();
        int i = 0;
        do {
            dates.add(start.plusDays(i)); // should work, because LocalDate is immutable
            i++;
        } while (start.plusDays(i).compareTo(end)<=0);
        return dates;
    }

    private List<Day> getDaysBetween(LocalDate start, LocalDate end) {
        fillGapsWithinRequest(start, end);
        return dayRepository.findAllByIdBetweenOrderByIdAsc(start, end);
    }


    private void fillGapsWithinRequest(LocalDate start, LocalDate end){
        List<LocalDate> dates = getLocalDatesBetween(start, end);
        for (LocalDate date :
                dates) {
            if(!dayRepository.existsById(date)){
                dayRepository.save(new Day(date));
            }
        }
    }

}

