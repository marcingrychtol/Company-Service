package pl.mdj.rejestrbiurowy.service.mappers;

import java.time.LocalDate;
import java.util.Date;

public interface DateMapper {
    Date toDate(LocalDate localDate);
    LocalDate toLocalDate(Date date);
    String dayOfWeekPL(LocalDate localDate);
    String dayOfWeekPL(Date date);
    String monthPL(LocalDate localDate);
    String monthPL(Date date);
    String valueWithZero(int i);
}
