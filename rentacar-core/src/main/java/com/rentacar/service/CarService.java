package com.rentacar.service;

import com.rentacar.model.dto.BookingParamsDto;
import com.rentacar.model.dto.CarDto;
import org.springframework.web.multipart.MultipartFile;
import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.exceptions.WrongInputDataException;

import java.time.LocalDate;
import java.util.List;


public interface CarService extends BasicService<CarDto, Long> {
    List<CarDto> getAvailableCarsByDay(LocalDate date);
    List<CarDto> getNotAvailableCarsByDay(LocalDate date);

    void addPhoto(MultipartFile photo, Long id) throws CannotFindEntityException, WrongInputDataException;

    List<CarDto> findAllByDay(LocalDate date);

    BookingParamsDto fillBookingParamsDto(BookingParamsDto bookingParams);
}
