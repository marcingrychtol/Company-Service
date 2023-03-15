package com.rentacar.service;

import com.rentacar.model.dto.DayDto;
import com.rentacar.model.entity.Day;
import com.rentacar.model.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.exceptions.EntityConflictException;
import com.rentacar.exceptions.EntityNotCompleteException;
import com.rentacar.model.mappers.DayMapper;
import com.rentacar.repository.DayRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DayServiceImpl implements DayService {

    DayRepository dayRepository;
    DayMapper dayMapper;

    @Autowired
    public DayServiceImpl(DayRepository dayRepository, @Lazy DayMapper dayMapper) {
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
    public List<DayDto> getDaysDtoAfter(LocalDate date) {
        return dayMapper.mapToDto(dayRepository.findAllByIdAfterOrderByIdAsc(date));
    }

    @Override
    public List<DayDto> getDaysDtoBefore(LocalDate date) {
        return dayMapper.mapToDto(dayRepository.findAllByIdBeforeOrderByIdAsc(date));
    }

    @Override
    public List<Trip> findTripsByDay(LocalDate date) {
        return dayRepository.findById(date).map(
                day1 -> day1.getTrips()
                        .stream()
                        .filter(trip -> !trip.getCancelled())
                        .collect(Collectors.toList())
        ).orElseGet(ArrayList::new);
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

    @Override
    public List<DayDto> getAllDto() {
        return dayMapper.mapToDto(dayRepository.findAll());
    }



    @Override
    public List<Day> getDaysBetween(LocalDate start, LocalDate end) {
        fillGapsWithinRequest(start, end);
        if (start.equals(end)){
            try {
                return Arrays.asList(findById(start) );
            } catch (CannotFindEntityException e) {
                e.printStackTrace();
            }
        }
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

