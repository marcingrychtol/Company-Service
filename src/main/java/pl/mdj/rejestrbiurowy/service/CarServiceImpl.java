package pl.mdj.rejestrbiurowy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
@Service
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {
    /*
     * Car
     * CarCategory
     */
    CarRepository carRepository;

}
