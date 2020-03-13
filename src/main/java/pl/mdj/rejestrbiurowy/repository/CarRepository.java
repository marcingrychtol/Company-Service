package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
}
