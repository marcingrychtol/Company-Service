package pl.mdj.rejestrbiurowy.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CarMapper implements BasicMapper<Car, CarDto> {

    CarRepository carRepository;

    @Autowired
    public CarMapper(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDto mapToDto(Car entity) {
        CarDto dto = new CarDto();
        Optional.ofNullable(entity.getId()).ifPresent(dto::setId);
        Optional.ofNullable(entity.getName()).ifPresent(dto::setName);
        Optional.ofNullable(entity.getRegistration()).ifPresent(dto::setRegistration);
        return dto;
    }

    @Override
    public List<CarDto> mapToDto(List<Car> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Car mapToEntity(CarDto dto) {
        Car entity = new Car();
        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
        Optional.ofNullable(dto.getRegistration()).ifPresent(entity::setRegistration);
        return entity;
    }

    @Override
    public List<Car> mapToEntity(List<CarDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
