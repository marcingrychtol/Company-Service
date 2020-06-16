package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

/**
 * Contains information to view on booking-car-calendar (from app direction)
 * If car is not available, contains information about occupying employee
 * List of this objects is wrapped in BookingDto
 * Related Car is defined in BookingDto object
 *
 * Get that object by geCarDayInfo() in DayService
 *
 * @field requested is set to true, if user wants to make reservation (into app direction)
 */
@Data
public class CarDayInfoDto {

//    from app
    private DateDto id;
    private Boolean available;
    private EmployeeDto employeeDto;
//    into app
    private Boolean requested;

}
