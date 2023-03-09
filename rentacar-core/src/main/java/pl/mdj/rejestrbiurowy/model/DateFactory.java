package pl.mdj.rejestrbiurowy.model;

import pl.mdj.rejestrbiurowy.model.dto.DateDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface DateFactory {
    String localDateTimeToString(LocalDateTime dateTime);
    DateDto getDateDto(LocalDate localDate);
}
