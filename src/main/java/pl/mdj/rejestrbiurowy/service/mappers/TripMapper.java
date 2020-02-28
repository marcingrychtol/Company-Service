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
import java.util.stream.Collectors;

@Component
public class TripMapper implements BasicMapper<Trip, TripDto> {

    CarRepository carRepository;
    EmployeeRepository employeeRepository;
    TripRepository tripRepository;
    CarMapper carMapper;
    EmployeeMapper employeeMapper;

    private static Logger LOG = LoggerFactory.getLogger(TripMapper.class);

    @Autowired
    public TripMapper(CarRepository carRepository, EmployeeRepository employeeRepository, TripRepository tripRepository, CarMapper carMapper, EmployeeMapper employeeMapper) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.tripRepository = tripRepository;
        this.carMapper = carMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public TripDto mapToDto(Trip entity) {
        TripDto dto = new TripDto();
        dto.setCar(carMapper
                .mapToDto(entity
                        .getCar()));
        dto.setEmployee(employeeMapper
                .mapToDto(entity
                        .getEmployee()));
        dto.setStartingDate(Date
                .from(entity
                        .getStartingDate()
                        .atStartOfDay(ZoneId.of("CET"))
                        .toInstant()));
        dto.setEndingDate(Date
                .from(entity
                        .getEndingDate()
                        .atStartOfDay(ZoneId.of("CET"))
                        .toInstant()));
        dto.setAdditionalMessage(" ");
        if (entity.getAdditionalMessage() != null){
            dto.setAdditionalMessage(entity.getAdditionalMessage());
        }
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

        Trip entity;


        if (dto.getId() != null) {
            entity = tripRepository
                    .findById(dto
                            .getId())
                    .orElse(new Trip());
            if (entity.getId() != null) {
                return entity;
            }
        }

        entity = new Trip();
        entity.setId(dto.getId());
        entity.setCar(carRepository
                .findById(dto
                        .getCarId())
                .orElse(new Car()));
        entity.setEmployee(employeeRepository
                .findById(dto
                        .getEmployeeId())
                .orElse(new Employee()));
        entity.setStartingDate(LocalDate
                .ofInstant(dto
                        .getStartingDate()
                        .toInstant(),
                        ZoneId.of("CET")));
        entity.setEndingDate(LocalDate
                .ofInstant(dto
                        .getEndingDate()
                        .toInstant(),
                        ZoneId.of("CET")));
        if (dto.getAdditionalMessage() != null){
            entity.setAdditionalMessage(dto.getAdditionalMessage());
        }
        return entity;
    }

    @Override
    public List<Trip> mapToEntity(List<TripDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
