package pl.mdj.rejestrbiurowy.model.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.FilterDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;

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

    public FilterDto completeTripData(FilterDto tripDto) {
        Optional.ofNullable(tripDto.getCarId())
                .flatMap(carId -> carRepository.findById(carId))
                .ifPresent(car -> tripDto.setCar(carMapper.mapToDto(car)));
        Optional.ofNullable(tripDto.getEmployeeId())
                .flatMap(empId -> employeeRepository.findById(empId))
                .ifPresent(emp -> tripDto.setEmployee(employeeMapper.mapToDto(emp)));
        return tripDto;
    }

    @Override
    public TripDto mapToDto(Trip entity) {
        TripDto dto = new TripDto();

        Optional.ofNullable(entity.getId())
                .ifPresent(dto::setId);
        Optional.ofNullable(entity.getCar())
                .ifPresent(car -> {
                    dto.setCar(carMapper.mapToDto(car));
                    dto.setCarId(entity.getCar().getId());
                });
        Optional.ofNullable(entity.getEmployee())
                .ifPresent(employee -> {
                    dto.setEmployee(employeeMapper.mapToDto(employee));
                    dto.setEmployeeId(entity.getEmployee().getId());
                });
        Optional.ofNullable(entity.getStartingDate())
                .ifPresent(date -> dto.setStartingDate(dateMapper.toDate(date)));
        Optional.ofNullable(entity.getEndingDate())
                .ifPresent(date -> dto.setEndingDate(dateMapper.toDate(date)));
        Optional.ofNullable(entity.getAdditionalMessage())
                .ifPresent(dto::setAdditionalMessage);
        Optional.ofNullable(entity.getCancelled())
                .ifPresent(dto::setCancelled);

        Optional.ofNullable(entity.getCreatedTime())
                .ifPresent(created -> dto
                        .setCreatedTime(dateMapper
                                .localDateTimeToString(created)));

        Optional.ofNullable(entity.getLastModifiedTime())
                .ifPresent(created -> dto
                        .setLastModifiedTime(dateMapper
                                .localDateTimeToString(created)));

        Optional.ofNullable(entity.getCancelledTime())
                .ifPresent(created -> dto
                        .setCancelledTime(dateMapper
                                .localDateTimeToString(created)));

        dto.setCancelled(entity.getCancelled());

        return dto;
    }

    @Override
    public List<TripDto> mapToDto(List<Trip> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Trip mapToEntity(TripDto dto) {
        Trip entity = new Trip();

        if (dto.getEndingDate() == null) {
            dto.setEndingDate(dto.getStartingDate());
        }

        Optional.ofNullable(dto.getId())
                .ifPresent(entity::setId);
        Optional.ofNullable(dto.getCar())
                .ifPresent(car -> entity.setCar(carMapper.mapToEntity(car)));
        if (entity.getCar() == null) {
            if (dto.getCarId() != null) {
                Optional<Car> car = carRepository.findById(dto.getCarId());
                car.ifPresent(entity::setCar);
            }
        }
        Optional.ofNullable(dto.getEmployee())
                .ifPresent(employee -> entity.setEmployee(employeeMapper.mapToEntity(employee)));
        if (entity.getEmployee() == null) {
            if (dto.getEmployeeId() != null) {
                Optional<Employee> employee = employeeRepository.findById(dto.getEmployeeId());
                employee.ifPresent(entity::setEmployee);
            }
        }
        Optional.ofNullable(dto.getStartingDate())
                .ifPresent(date -> entity.setStartingDate(dateMapper.toLocalDate(date)));
        Optional.ofNullable(dto.getEndingDate())
                .ifPresent(date -> entity.setEndingDate(dateMapper.toLocalDate(date)));
        Optional.ofNullable(dto.getAdditionalMessage())
                .ifPresent(entity::setAdditionalMessage);

        entity.setCancelled(dto.getCancelled());

        // TODO: creation and modification times and etc. are not transfered
        // cancellation info is not transfered this way

        return entity;
    }

    @Override
    public List<Trip> mapToEntity(List<TripDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
