package pl.mdj.rejestrbiurowy.clientaccess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    private Long carId;
    private Long employeeId;
    private LocalDate date;
}
