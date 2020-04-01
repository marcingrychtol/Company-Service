package pl.mdj.rejestrbiurowy.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.DayRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DayMapper implements BasicMapper<Day, DayDto> {

    CarMapper carMapper;
    DayRepository dayRepository;

    @Autowired
    public DayMapper(CarMapper carMapper, DayRepository dayRepository) {
        this.carMapper = carMapper;
        this.dayRepository = dayRepository;
    }

    @Override
    public DayDto mapToDto(Day entity) {
        DayDto dto = new DayDto();
        dto.setId(entity.getId());
        dto.setCarDtoList(entity.getTrips().stream()
                .map(Trip::getCar)
                .map(car -> carMapper.mapToDto(car))
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public List<DayDto> mapToDto(List<Day> entityList) {
        return entityList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Day mapToEntity(DayDto dto) {
        Optional<Day> entityOptional = dayRepository.findById(dto.getId());
        if (entityOptional.isPresent()){
            return entityOptional.get();
        }
        Day entity = new Day();
        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        return entity;
    }

    @Override
    public List<Day> mapToEntity(List<DayDto> dtoList) {
        return dtoList.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
