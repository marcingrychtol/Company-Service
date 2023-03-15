package com.rentacar.model;

import com.rentacar.model.dto.DateDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateFactory {
    String localDateTimeToString(LocalDateTime dateTime);
    DateDto getDateDto(LocalDate localDate);
}
