package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Used to transfer data necessary to view car-calendar
 * Default view is one week
 */

@Data
public class BookingParamsDto {

    private CarDto car;
    private Long carId;
    private EmployeeDto employee;
    private Long employeeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestedDate;
    private Integer scope = 14;
    private List<CarDayInfoDto> carDayInfoList;

}
