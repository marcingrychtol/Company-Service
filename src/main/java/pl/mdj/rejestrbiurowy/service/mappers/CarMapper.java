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
    public CarDto mapToDto(Car inputEntity) {
        CarDto dto = new CarDto();
        dto.setId(inputEntity.getId());
        dto.setName(inputEntity.getName());
        dto.setRegistration(inputEntity.getRegistration());
        return dto;
    }

    @Override
    public List<CarDto> mapToDto(List<Car> inputEntityList) {
        return inputEntityList.stream()
                .map(e -> mapToDto(e))
                .collect(Collectors.toList());
    }

    @Override
    public Car mapToEntity(CarDto inputDto) {
        Car entity;

        if (inputDto.getId() != null) {
            entity = carRepository.findById(inputDto.getId()).orElse(new Car());
            if (entity.getId() != null) {
                return entity;
            }
        }

        entity = new Car();
        entity.setId(inputDto.getId());
        entity.setName(inputDto.getName());
        entity.setRegistration(inputDto.getRegistration());

        return entity;
    }

    @Override
    public List<Car> mapToEntity(List<CarDto> inputDtoList) {
        return inputDtoList.stream()
                .map(c -> mapToEntity(c))
                .collect(Collectors.toList());
    }
}
