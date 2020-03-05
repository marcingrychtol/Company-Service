package pl.mdj.rejestrbiurowy.service.interfaces;

import pl.mdj.rejestrbiurowy.model.dto.TripDto;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    List<TripDto> findAllByEmployee_Id(Long id);
    List<TripDto> findAllByCar_Id(Long id);
    List<TripDto> findAllByStartingDateEquals(LocalDate date);
 }


