package pl.mdj.rejestrbiurowy.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.mappers.CarMapper;

import java.util.List;


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

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public List<CarDto> getAll() {
        return null;
    }

    @Override
    public CarDto findOne(Long id) {
        return null;
    }

    @Override
    public CarDto addOne(CarDto carDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

}
