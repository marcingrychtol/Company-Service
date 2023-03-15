package com.rentacar.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Used to transfer data necessary to view car-calendar
 * Can set default view here
 */

@Data
public class BookingParamsDto {

    private CarDto car;
    private Long carId;
    private EmployeeDto employee;
    private Long employeeId;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate requestedDate;
    private Integer scope = 28;
    private List<CarDayInfoDto> carDayInfoList;
    private String additionalMessage;

}
