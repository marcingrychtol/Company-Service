package pl.mdj.rejestrbiurowy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class TripDto{
    private Long id; // only individual database registry id
    private Long carId;
    private CarDto car;
    private Long employeeId;
    private EmployeeDto employee;

    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date startingDate;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date endingDate;

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

    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date filterStartingDate;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date filterEndingDate;

    private String filterCreatedTime;
    private String filterLastModifiedTime;
    private Boolean filterCancelled = false;

    private String filterAdditionalMessage;

    private Integer bookingScope;

}
