package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mdj.rejestrbiurowy.entity.Car;

public interface CarRepository extends CrudRepository<Car,Long> {
}
