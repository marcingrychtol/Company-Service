package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.dto.CarDayInfoDto;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface DayService {
    Day findById(LocalDate id) throws CannotFindEntityException;
    void addTripToDay(Trip trip) throws EntityNotCompleteException, EntityConflictException;
    List<LocalDate> getLocalDatesBetween(LocalDate start, LocalDate end);
    List<DayDto> getAllDto();
    List<DayDto> getDaysDtoBetween(LocalDate start, LocalDate end);
    List<Day> getDaysBetween(LocalDate start, LocalDate end);
    List<DayDto> getDaysDtoAfter(LocalDate date);
    List<DayDto> getDaysDtoBefore(LocalDate date);

    List<Trip> findTripsByDay(LocalDate date);
}
