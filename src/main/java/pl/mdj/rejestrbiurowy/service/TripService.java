package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    List<TripDto> findAllActiveByDate(LocalDate date);
    List<TripDto> findAllByDate(LocalDate date);

    List<TripDto> findByFilter(TripDto filter);

    TripDto completeFilterDtoData(TripDto tripDto);


}


