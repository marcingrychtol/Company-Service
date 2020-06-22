package pl.mdj.rejestrbiurowy.service;

import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;

import java.time.LocalDate;
import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    List<TripDto> findAllActiveByDate(LocalDate date);
    List<TripDto> findAllByDate(LocalDate date);
    List<TripDto> findByFilter(TripDto filter);
    TripDto completeFilterDtoData(TripDto tripDto);
    List<TripDto> getTripsByCar(LocalDate startingDate, Car car, Integer scope);
    List<TripDto> findConflictedTrips(List<TripDto> resolvedTrips);

    void addAll(EmployeeDto employeeDto, List<TripDto> trips);
}


