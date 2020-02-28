package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TripDto {
    private Long id;
    private Long carId;
    private CarDto car;
    private Long employeeId;
    private EmployeeDto employee;

    private Date startingDate;
    private Date endingDate;

    private String additionalMessage;

    /**
     *  Checks if Trip has all parameters - except endingDate. If ending date has not been set,
     *  it is presumed to be the same as startingDate.
     * @return
     */
    public boolean isComplete(){
        if (car == null) {
            return false;
        }
        if (employee == null) {
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
