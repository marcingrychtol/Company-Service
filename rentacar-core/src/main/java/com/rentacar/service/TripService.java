package com.rentacar.service;

import com.rentacar.model.dto.BookingParamsDto;
import com.rentacar.model.dto.TripDto;
import com.rentacar.model.entity.Car;
import com.rentacar.exceptions.EntityNotCompleteException;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    List<TripDto> findAllActiveByDate(LocalDate date);
    List<TripDto> findAllByDate(LocalDate date);
    List<TripDto> findByFilter(TripDto filter);
    TripDto completeFilterDtoData(TripDto tripDto);
    List<TripDto> getTripsByCar(LocalDate startingDate, Car car, Integer scope);

    List<TripDto> findConflictedTrips(List<TripDto> requestedTrips);

    List<TripDto> addAll(BookingParamsDto bookingParams) throws EntityNotCompleteException;

    List<TripDto> joinRequestedTrips(BookingParamsDto bookingParams);
}


