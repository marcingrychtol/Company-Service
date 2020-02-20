package pl.mdj.rejestrbiurowy.clientaccess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    private Long carId;
    private Long employeeId;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
}
