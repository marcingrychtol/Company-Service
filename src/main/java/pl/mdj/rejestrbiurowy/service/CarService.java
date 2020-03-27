package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.model.dto.CarDto;

import java.time.LocalDate;
import java.util.Set;


public interface CarService extends BasicService<CarDto, Long> {
    Set<CarDto> getAvailable(LocalDate date);
}
