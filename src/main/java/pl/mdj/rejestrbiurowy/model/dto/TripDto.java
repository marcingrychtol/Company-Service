package pl.mdj.rejestrbiurowy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TripDto {
    private Long id; // only individual database registry id
    private Long carId;
    private CarDto car;
    private Long employeeId;
    private EmployeeDto employee;

    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date startingDate;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date endingDate;
    private Integer durationDays;

    private String createdTime;
    private String lastModifiedTime;
    private String cancelledTime;
    private Boolean cancelled;

    private String additionalMessage;

    /**
     * Checks if Trip has all parameters - except endingDate. If ending date has not been set,
     * it is presumed to be the same as startingDate.
     *
     * @return
     */
    public boolean isComplete() {
        if (carId == null) {
            return false;
        }
        if (employeeId == null) {
            return false;
        }
        if (startingDate == null) {
            return false;
        }
        return true;
    }

    public boolean hasMessage() {
        if (additionalMessage == null) {
            return false;
        }
        return true;
    }
}
