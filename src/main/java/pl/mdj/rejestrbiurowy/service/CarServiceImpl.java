package pl.mdj.rejestrbiurowy.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.mappers.CarMapper;
import pl.mdj.rejestrbiurowy.service.mappers.DateMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@NoArgsConstructor
public class CarServiceImpl implements CarService {

    /*
     * Car
     * CarCategory
     */

    private Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    CarRepository carRepository;
    CarMapper carMapper;
    TripRepository tripRepository;
    DateMapper dateMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper, TripRepository tripRepository, DateMapper dateMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.tripRepository = tripRepository;
        this.dateMapper = dateMapper;
    }

    @Override
    public List<CarDto> getAll() {
        return carMapper.mapToDto(carRepository.findAll());
    }

    @Override
    public CarDto findById(Long id) throws CannotFindEntityException {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()){
            return carMapper.mapToDto(carOptional.get());
        } else {
            throw new CannotFindEntityException("Cannot find car of id: " + id);
        }
    }

    @Override
    public CarDto addOne(CarDto carDto) {
        carRepository.save(carMapper.mapToEntity(carDto));
        return carDto;
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Set<CarDto> getAvailable(LocalDate date) {
        List<Car> notAvailableCarList = tripRepository.findAllByStartingDateEquals(date).stream()
                .map(Trip::getCar)
                .collect(Collectors.toList());
        return carRepository.findAll().stream()
                .filter(c -> !notAvailableCarList.contains(c))
                .map(c -> carMapper.mapToDto(c))
                .collect(Collectors.toSet());
    }

}
