package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Used to transfer data necessary to view car-calendar
 * Default view is one week
 */

@Data
public class BookingParamsDto {

    private Integer scope = 14;
    private Long carId;
    private CarDto car;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestedDate;

}
