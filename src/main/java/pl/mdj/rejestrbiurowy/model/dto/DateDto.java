package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
public class DateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private Date date;
    private String monthPL;
    private String monthValueWithZero;
    private String monthValueWithZeroJS;
    private String dayOfWeekPL;
    private String dayValueWithZeroJS;
    private String dayValueWithZero;
    private String year;
    public String getLocalDateFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(localDate);
    }
}
