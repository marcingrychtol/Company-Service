package pl.mdj.rejestrbiurowy.service.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TripMapper implements BasicMapper<Trip, TripDto> {

    CarRepository carRepository;
    EmployeeRepository employeeRepository;
    TripRepository tripRepository;
    CarMapper carMapper;
    EmployeeMapper employeeMapper;
    DateMapper dateMapper;

    private static Logger LOG = LoggerFactory.getLogger(TripMapper.class);

    @Autowired
    public TripMapper(CarRepository carRepository, EmployeeRepository employeeRepository, TripRepository tripRepository, CarMapper carMapper, EmployeeMapper employeeMapper, DateMapper dateMapper) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.tripRepository = tripRepository;
        this.carMapper = carMapper;
        this.employeeMapper = employeeMapper;
        this.dateMapper = dateMapper;
    }

    @Override
    public TripDto mapToDto(Trip entity) {
        TripDto dto = new TripDto();

        Optional.ofNullable(entity.getId()).ifPresent(dto::setId);
        Optional.ofNullable(entity.getCar()).ifPresent(car -> dto.setCar(carMapper.mapToDto(car)));
        Optional.ofNullable(entity.getEmployee()).ifPresent(employee -> dto.setEmployee(employeeMapper.mapToDto(employee)));
        Optional.ofNullable(entity.getStartingDate()).ifPresent(date -> dto.setStartingDate(dateMapper.toDate(date)));
        Optional.ofNullable(entity.getAdditionalMessage()).ifPresent(dto::setAdditionalMessage);

        return dto;
    }

    @Override
    public List<TripDto> mapToDto(List<Trip> entityList) {
        return  entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Trip mapToEntity(TripDto dto) {
        Trip entity = new Trip();

        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        Optional.ofNullable(dto.getCar()).ifPresent(car -> entity.setCar(carMapper.mapToEntity(car)));
        Optional.ofNullable(dto.getEmployee()).ifPresent(employee -> entity.setEmployee(employeeMapper.mapToEntity(employee)));
        Optional.ofNullable(dto.getStartingDate()).ifPresent(date -> entity.setStartingDate(dateMapper.toLocalDate(date)));
        Optional.ofNullable(dto.getAdditionalMessage()).ifPresent(entity::setAdditionalMessage);

        return entity;
    }

    @Override
    public List<Trip> mapToEntity(List<TripDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
