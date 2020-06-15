package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

/**
 * Contains information to view on booking-car-calendar, if the car is available
 * If car is not available, contains information about employee
 * List of this objects is sent in BookingDto
 * Car is defined in BookingDto object
 */
@Data
public class CarDayInfoDto {

    private DateDto id;
    private Boolean available;
    private EmployeeDto employeeDto;

}
