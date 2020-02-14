package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pl.mdj.rejestrbiurowy.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
}
