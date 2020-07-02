package pl.mdj.rejestrbiurowy.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


/**
 * Object used to transfer data between application and template engine
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class TripDto{
    private Long id; // only individual database registry id

    private Long carId;
    private CarDto car;
    private Long employeeId;
    private EmployeeDto employee;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startingDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endingDate;

    private String createdTime;
    private String lastModifiedTime;  // TODO: not working
    private String cancelledTime;
    private Boolean cancelled = false;

    private String additionalMessage;

    private Long filterId;
    private Long filterCarId;
    private CarDto filterCar;
    private Long filterEmployeeId;
    private EmployeeDto filterEmployee;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate filterStartingDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate filterEndingDate;

    private String filterCreatedTime;
    private String filterLastModifiedTime;
    private Boolean filterCancelled = false;

    private String filterAdditionalMessage;


}
