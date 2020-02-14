package pl.mdj.rejestrbiurowy.service.interfaces;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.repository.CarRepository;

import java.util.List;


public interface CarService {

    String addCar(Car car);

    List<Car> findAll();
}
