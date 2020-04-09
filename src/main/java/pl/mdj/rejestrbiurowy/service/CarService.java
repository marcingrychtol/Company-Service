package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.model.dto.CarDto;

import java.time.LocalDate;
import java.util.List;


public interface CarService extends BasicService<CarDto, Long> {
    List<CarDto> getAvailableCarsByDay(LocalDate date);
    List<CarDto> getNotAvailableCarsByDay(LocalDate date);
}
