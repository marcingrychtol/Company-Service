package pl.mdj.rejestrbiurowy.model.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public String dayOfWeekPL(LocalDate localDate) {
        switch (localDate.getDayOfWeek()){
            case MONDAY: return "Poniedziałek";
            case TUESDAY: return "Wtorek";
            case WEDNESDAY: return "Środa";
            case THURSDAY: return "Czwartek";
            case FRIDAY: return "Piątek";
            case SATURDAY: return "Sobota";
            case SUNDAY: return "Niedziela";
            default: return null;
        }
    }

    @Override
    public String dayOfWeekPL(Date date) {
        return dayOfWeekPL(toLocalDate(date));
    }

    @Override
    public String monthPL(LocalDate localDate) {
        switch (localDate.getMonth()){
            case JANUARY: return "Stycznia";
            case FEBRUARY: return "Lutego";
            case MARCH: return "Marca";
            case APRIL: return "Kwietnia";
            case MAY: return "Maja";
            case JUNE: return "Czerwieca";
            case JULY: return "Lipca";
            case AUGUST: return "Sierpienia";
            case SEPTEMBER: return "Wrzesienia";
            case OCTOBER: return "Października";
            case NOVEMBER: return "Listopada";
            case DECEMBER: return "Grudnia";
            default:return null;
        }
    }

    @Override
    public String monthPL(Date date) {
        return dayOfWeekPL(toLocalDate(date));
    }

    @Override
    public String valueWithZero(int i) {
        if (i < 10){
            return "0"+i;
        }
        return String.valueOf(i);
    }

    @Override
    public String valueWithZeroForJS(int i) {
        if (i < 10){
            return "\"0\"+"+i;
        }
        return String.valueOf(i);
    }

    @Override
    public String localDateTimeToString(LocalDateTime dateTime) {
        return String.format("%s/%s/%d",
                valueWithZero(dateTime.getDayOfMonth()),
                valueWithZero(dateTime.getMonthValue()),
                dateTime.getYear());
    }


}
