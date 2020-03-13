package pl.mdj.rejestrbiurowy.service.mappers;

import java.time.LocalDate;
import java.util.Date;

public interface DateMapper {
    Date toDate(LocalDate localDate);
    LocalDate toLocalDate(Date date);
}
