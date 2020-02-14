package pl.mdj.rejestrbiurowy.service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;

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

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public String addCar(Car car){
        carRepository.save(car);
        LOG.info("Car is saved", car);
        return "";
    }

    @Override
    public List<Car> findAll() {
        return (List<Car>) carRepository.findAll();
    }

}
