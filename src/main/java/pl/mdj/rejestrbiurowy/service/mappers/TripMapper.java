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
        dto.setCarDto(carMapper
                .mapToDto(entity
                        .getCar()));
        dto.setEmployeeDto(employeeMapper
                .mapToDto(entity
                        .getEmployee()));
        dto.setStartingDate(entity.getStartingDate());
        dto.setEndingDate(entity.getEndingDate());
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
                        .getCarDto()
                        .getId())
                .orElse(new Car()));
        entity.setEmployee(employeeRepository
                .findById(dto
                        .getEmployeeDto()
                        .getId())
                .orElse(new Employee()));
        entity.setStartingDate(dto.getStartingDate());
        entity.setEndingDate(dto.getEndingDate());
        return entity;
    }

    @Override
    public List<Trip> mapToEntity(List<TripDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
