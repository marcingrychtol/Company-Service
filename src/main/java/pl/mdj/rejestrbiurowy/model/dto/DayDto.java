package pl.mdj.rejestrbiurowy.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all data of trips during specific day
 */
@Getter
@Setter
@EqualsAndHashCode
public class DayDto {
    private DateDto id;
    private List<Long> availableCars = new ArrayList<>(); // filled by DayMapper
    private List<TripDto> trips = new ArrayList<>(); // filled by DayMapper
}
