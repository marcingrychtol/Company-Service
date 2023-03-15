package com.rentacar.service;

import com.rentacar.model.dto.DayDto;
import com.rentacar.model.entity.Day;
import com.rentacar.model.entity.Trip;
import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.exceptions.EntityConflictException;
import com.rentacar.exceptions.EntityNotCompleteException;

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
