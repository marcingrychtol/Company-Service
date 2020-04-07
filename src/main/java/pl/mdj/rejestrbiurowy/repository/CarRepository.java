package pl.mdj.rejestrbiurowy.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    void deleteById(Long id) throws DataIntegrityViolationException;
    Optional<Car> findByRegistrationEquals(String registration);
}
