package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
}
