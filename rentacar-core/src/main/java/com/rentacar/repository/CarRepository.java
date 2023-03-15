package com.rentacar.repository;

import com.rentacar.model.entity.Car;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    void deleteById(Long id) throws DataIntegrityViolationException;
    Optional<Car> findByRegistrationEquals(String registration);
    List<Car> findAllByOrderByBrand();
}
