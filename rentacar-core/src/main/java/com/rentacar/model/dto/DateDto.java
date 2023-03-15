package com.rentacar.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Object designed to transfer specifically formatted date elements into template engine
 *
 * Do not create that object manually, only by DateMapper
 */
@Data
@NoArgsConstructor
public class DateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String monthPL;
    private String monthValueWithZero;
    private String monthValueWithZeroJS;
    private String dayOfWeekPL;
    private String dayOfWeekPLShort;
    private String dayValueWithZeroJS;
    private String dayValueWithZero;
    private String year;
    public String getLocalDateFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatter.format(date);
    }
}
