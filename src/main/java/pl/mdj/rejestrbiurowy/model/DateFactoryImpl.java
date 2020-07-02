package pl.mdj.rejestrbiurowy.model;

import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.DateDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateFactoryImpl implements DateFactory {

    private String dayOfWeekPL(LocalDate localDate) {
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

    private String dayOfWeekPLShort(LocalDate localDate) {
        switch (localDate.getDayOfWeek()){
            case MONDAY: return "Pon";
            case TUESDAY: return "Wto";
            case WEDNESDAY: return "Śro";
            case THURSDAY: return "Czw";
            case FRIDAY: return "Pią";
            case SATURDAY: return "Sob";
            case SUNDAY: return "Nie";
            default: return null;
        }
    }


    private String monthPL(LocalDate localDate) {
        switch (localDate.getMonth()){
            case JANUARY: return "Stycznia";
            case FEBRUARY: return "Lutego";
            case MARCH: return "Marca";
            case APRIL: return "Kwietnia";
            case MAY: return "Maja";
            case JUNE: return "Czerwca";
            case JULY: return "Lipca";
            case AUGUST: return "Sierpnia";
            case SEPTEMBER: return "Wrzesnia";
            case OCTOBER: return "Października";
            case NOVEMBER: return "Listopada";
            case DECEMBER: return "Grudnia";
            default:return null;
        }
    }

    private String valueWithZero(int i) {
        if (i < 10){
            return "0"+i;
        }
        return String.valueOf(i);
    }

    private String valueWithZeroForJS(int i) {
        if (i < 10){
            return "\"0\"+"+i;
        }
        return String.valueOf(i);
    }

    @Override
    public String localDateTimeToString(LocalDateTime dateTime) {
        return String.format("%s/%s/%d %s:%s %ss",
                valueWithZero(dateTime.getDayOfMonth()),
                valueWithZero(dateTime.getMonthValue()),
                dateTime.getYear(),
                valueWithZero(dateTime.getHour()),
                valueWithZero(dateTime.getMinute()),
                valueWithZero(dateTime.getSecond()));
    }

    @Override
    public DateDto getDateDto(LocalDate localDate) {
        DateDto dateDto = new DateDto();
        dateDto.setDate(localDate);
        dateDto.setDayOfWeekPL(dayOfWeekPL(localDate));
        dateDto.setDayOfWeekPLShort(dayOfWeekPLShort(localDate));
        dateDto.setDayValueWithZero(valueWithZero(localDate.getDayOfMonth()));
        dateDto.setDayValueWithZeroJS(valueWithZeroForJS(localDate.getDayOfMonth()));
        dateDto.setMonthPL(monthPL(localDate));
        dateDto.setMonthValueWithZero(valueWithZero(localDate.getMonthValue()));
        dateDto.setMonthValueWithZeroJS(valueWithZeroForJS(localDate.getMonthValue()));
        dateDto.setYear(String.valueOf(localDate.getYear()));
        return dateDto;
    }

}
