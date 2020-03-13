package pl.mdj.rejestrbiurowy.service.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateMapperImpl implements DateMapper {
    @Override
    public Date toDate(LocalDate localDate) {
        return Date.from(localDate
                        .atStartOfDay(ZoneId.of("CET"))
                        .toInstant());
    }

    @Override
    public LocalDate toLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.of("CET"));
    }

}
