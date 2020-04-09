package pl.mdj.rejestrbiurowy.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.repository.DayRepository;
import pl.mdj.rejestrbiurowy.service.CarService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DayMapper implements BasicMapper<Day, DayDto> {

    CarMapper carMapper;
    DayRepository dayRepository;
    CarService carService;
    DateMapper dateMapper;

    @Autowired
    public DayMapper(CarMapper carMapper, DayRepository dayRepository, DateMapper dateMapper, CarService carService) {
        this.carMapper = carMapper;
        this.dayRepository = dayRepository;
        this.dateMapper = dateMapper;
        this.carService = carService;
    }

    @Override
    public DayDto mapToDto(Day entity) {
        DayDto dto = new DayDto();
        dto.setId(dateMapper.getDateDto(entity.getId()));
//        dto.setAvailableCars(entity.getTrips().stream()
//                .map(Trip::getCar)
//                .map(car -> carMapper.mapToDto(car))
//                .collect(Collectors.toList()));
        dto.setAvailableCars(carService.getAvailableCarsByDay(entity.getId()));
        dto.setNotAvailableCars(carService.getNotAvailableCarsByDay(entity.getId()));
        return dto;
    }

    @Override
    public List<DayDto> mapToDto(List<Day> entityList) {
        return entityList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Day mapToEntity(DayDto dto) {
        Optional<Day> entityOptional = dayRepository.findById(dto.getId().getLocalDate());
        if (entityOptional.isPresent()){
            return entityOptional.get();
        }
        Day entity = new Day();
        Optional.ofNullable(dto.getId().getLocalDate()).ifPresent(entity::setId);
        return entity;
    }

    @Override
    public List<Day> mapToEntity(List<DayDto> dtoList) {
        return dtoList.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
