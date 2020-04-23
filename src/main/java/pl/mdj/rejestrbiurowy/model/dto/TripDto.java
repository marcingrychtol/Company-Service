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
public class TripDto implements Comparable<TripDto>{
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
    private String lastModifiedTime;  // TODO: not working
    private String cancelledTime;
    private Boolean cancelled = false;

    private String additionalMessage;


    @Override
    public int compareTo(TripDto o) {
        return o.getStartingDate().compareTo(this.startingDate);
    }
}
