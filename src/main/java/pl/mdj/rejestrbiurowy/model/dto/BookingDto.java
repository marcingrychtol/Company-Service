package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * For transferring booking parameters between
 * - booking-car-calendar
 * - CarService
 * for reservation establishment
 *
 * map keys are dates (LocalDate)
 * [0] value is availability (outside application data transfer)
 * [1] value is reservation (into application data transfer)
 */
@Data
public class BookingDto {

    private CarDto car;
    private Long carId;

    private Long scope;

    private List<CarDayInfoDto> carDayInfoDtoList;
}
