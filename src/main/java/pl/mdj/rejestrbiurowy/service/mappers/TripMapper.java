package pl.mdj.rejestrbiurowy.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripMapper implements BasicMapper<Trip, TripDto> {

    CarService carService;
    EmployeeService employeeService;

    @Autowired
    public TripMapper(CarService carService, EmployeeService employeeService) {
        this.carService = carService;
        this.employeeService = employeeService;
    }

    @Override
    public TripDto mapToDto(Trip inputEntity) {
        TripDto tripDto = new TripDto();
        tripDto.setCarId(inputEntity.getCar().getId());
        tripDto.setEmployeeId(inputEntity.getEmployee().getId());
        tripDto.setStartingDate(Date
                .from(inputEntity
                        .getStartingDateTime()
                        .atZone(ZoneId.of("CET"))
                        .toInstant()));
        tripDto.setEndingDate(Date
                .from(inputEntity
                        .getEndingDateTime()
                        .atZone(ZoneId.of("CET"))
                        .toInstant()));
        return null;
    }

    @Override
    public List<TripDto> mapToDto(List<Trip> inputEntityList) {
        return  inputEntityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Trip mapToEntity(TripDto inputDto) {
        Trip trip = new Trip();
        trip.setCar(carService.getOne(inputDto.getCarId()));
        trip.setEmployee(employeeService.getOne(inputDto.getEmployeeId()));
        trip.setStartingDateTime(LocalDateTime
                .ofInstant(inputDto
                        .getStartingDate()
                        .toInstant(),
                        ZoneId.of("CET")));
        trip.setEndingDateTime(LocalDateTime
                .ofInstant(inputDto
                        .getEndingDate()
                        .toInstant(),
                        ZoneId.of("CET")));
        return trip;
    }

    @Override
    public List<Trip> mapToEntity(List<TripDto> inputDtoList) {
        return inputDtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
