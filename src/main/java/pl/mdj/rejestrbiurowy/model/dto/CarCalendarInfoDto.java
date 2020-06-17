package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.util.List;

/**
 * For transferring reservation parameters between
 * - booking-car-calendar
 * - CarService
 * for reservation establishment
 */

@Data
public class CarCalendarInfoDto {

//    from app
    private CarDto car;
    private Long carId;
    private List<CarDayInfoDto> carDayInfoList;
}
