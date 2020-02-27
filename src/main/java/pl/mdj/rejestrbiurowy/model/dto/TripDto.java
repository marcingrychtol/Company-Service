package pl.mdj.rejestrbiurowy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    private Long carId;
    private Long employeeId;
    private Date startingDate;
    private Date endingDate;
    private String additionalMessage;

    /**
     *  Checks if Trip has all parameters - except endingDate. If ending date has not been set,
     *  it is presumed to be the same as startingDate.
     * @return
     */
    public boolean isComplete(){
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

    public boolean hasMessage(){
        if (additionalMessage == null) {
            return false;
        }
        return true;
    }
}
