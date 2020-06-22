package pl.mdj.rejestrbiurowy.service;

import org.springframework.web.multipart.MultipartFile;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.*;
import pl.mdj.rejestrbiurowy.model.entity.Car;

import java.time.LocalDate;
import java.util.List;


public interface CarService extends BasicService<CarDto, Long> {
    List<CarDto> getAvailableCarsByDay(LocalDate date);
    List<CarDto> getNotAvailableCarsByDay(LocalDate date);

    void addPhoto(MultipartFile photo, Long id) throws CannotFindEntityException, WrongInputDataException;

    List<CarDto> findAllByDay(LocalDate date);

    BookingParamsDto fillBookingParamsDto(BookingParamsDto bookingParams);
}
