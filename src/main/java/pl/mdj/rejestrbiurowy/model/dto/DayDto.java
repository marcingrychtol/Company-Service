package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DayDto {
    private DateDto id;
    private List<CarDto> availableCars = new ArrayList<>();
    private List<CarDto> notAvailableCars = new ArrayList<>();
}
