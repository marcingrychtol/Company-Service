package pl.mdj.rejestrbiurowy.service;

import org.springframework.data.domain.Sort;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.service.interfaces.BasicService;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    List<TripDto> findAllByEmployee_Id(Long id);
    List<TripDto> findAllByCar_Id(Long id);
    List<TripDto> findAllByStartingDateEquals(LocalDate date);
 }


