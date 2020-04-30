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
        Optional.ofNullable(entity.getBrand()).ifPresent(dto::setBrand);
        Optional.ofNullable(entity.getModel()).ifPresent(dto::setModel);
        Optional.ofNullable(entity.getRegistration()).ifPresent(dto::setRegistration);
        Optional.ofNullable(entity.getInspectionExpiration()).ifPresent(dto::setInspectionExpiration);
        Optional.ofNullable(entity.getInsuranceExpiration()).ifPresent(dto::setInsuranceExpiration);
        Optional.ofNullable(entity.getMileage().intValue()).ifPresent(dto::setMileage);
        dto.setCancelled(entity.getCancelled());

        Optional.ofNullable(entity.getImage()).ifPresent(dto::setImage);
        Optional.ofNullable(entity.getImageName()).ifPresent(dto::setImageName);
        Optional.ofNullable(entity.getFileType()).ifPresent(dto::setFileType);

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
        Optional.ofNullable(dto.getBrand()).ifPresent(entity::setBrand);
        Optional.ofNullable(dto.getModel()).ifPresent(entity::setModel);
        Optional.ofNullable(dto.getRegistration()).ifPresent(entity::setRegistration);
        Optional.ofNullable(dto.getInspectionExpiration()).ifPresent(entity::setInspectionExpiration);
        Optional.ofNullable(dto.getInsuranceExpiration()).ifPresent(entity::setInsuranceExpiration);
        Optional.of(dto.getMileage()).ifPresent(mileage -> entity.setMileage(Long.valueOf(mileage)));
        entity.setCancelled(dto.isCancelled());

        return entity;
    }

    public Car mapToEntityJoin(CarDto dto, Car entity) {
        Car newEntity = new Car();
        Optional.ofNullable(dto.getBrand()).ifPresentOrElse(newEntity::setBrand,()-> newEntity.setBrand(entity.getBrand()));
        Optional.ofNullable(dto.getModel()).ifPresentOrElse(newEntity::setModel,()-> newEntity.setModel(entity.getModel()));
        Optional.ofNullable(dto.getRegistration()).ifPresentOrElse(newEntity::setRegistration,()-> newEntity.setRegistration(entity.getRegistration()));
        newEntity.setMileage(0L);
//        Optional.ofNullable(dto.getInspectionExpiration()).ifPresent(newEntity::setInspectionExpiration);
//        Optional.ofNullable(dto.getInsuranceExpiration()).ifPresent(newEntity::setInsuranceExpiration);

        return newEntity;
    }

    @Override
    public List<Car> mapToEntity(List<CarDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
