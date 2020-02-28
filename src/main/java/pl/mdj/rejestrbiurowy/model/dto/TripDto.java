package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TripDto {
    private Long id;
    private CarDto carDto;
    private EmployeeDto employeeDto;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String additionalMessage;

    /**
     *  Checks if Trip has all parameters - except endingDate. If ending date has not been set,
     *  it is presumed to be the same as startingDate.
     * @return
     */
    public boolean isComplete(){
        if (carDto == null) {
            return false;
        }
        if (employeeDto == null) {
            return false;
        }
        if (startingDate == null) {
            return false;
        }
        return true;
    }

    public boolean hasMessage(){
        if (additionalMessage == null) {
            return false;
        }
        return true;
    }
}
