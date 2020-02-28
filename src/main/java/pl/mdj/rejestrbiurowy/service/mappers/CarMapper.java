package pl.mdj.rejestrbiurowy.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.repository.CarRepository;

import java.util.List;
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
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRegistration(entity.getRegistration());
        return dto;
    }

    @Override
    public List<CarDto> mapToDto(List<Car> entityList) {
        return entityList.stream()
                .map(e -> mapToDto(e))
                .collect(Collectors.toList());
    }

    @Override
    public Car mapToEntity(CarDto dto) {
        Car entity;

        if (dto.getId() != null) {
            entity = carRepository.findById(dto.getId()).orElse(new Car());
            if (entity.getId() != null) {
                return entity;
            }
        }

        entity = new Car();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setRegistration(dto.getRegistration());

        return entity;
    }

    @Override
    public List<Car> mapToEntity(List<CarDto> dtoList) {
        return dtoList.stream()
                .map(c -> mapToEntity(c))
                .collect(Collectors.toList());
    }
}
