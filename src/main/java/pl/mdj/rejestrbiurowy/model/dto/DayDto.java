package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DayDto {
    private LocalDate id;
    private List<CarDto> carDtoList = new ArrayList<>();
}
