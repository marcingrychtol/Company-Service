package pl.mdj.rejestrbiurowy.model.mappers;

import pl.mdj.rejestrbiurowy.model.dto.DateDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface DateMapper {
    Date toDate(LocalDate localDate);
    LocalDate toLocalDate(Date date);
    String dayOfWeekPL(LocalDate localDate);
    String dayOfWeekPL(Date date);
    String monthPL(LocalDate localDate);
    String monthPL(Date date);
    String valueWithZero(int i);
    String valueWithZeroForJS(int i);
    String localDateTimeToString(LocalDateTime dateTime);
    DateDto getDateDto(LocalDate localDate);
}
