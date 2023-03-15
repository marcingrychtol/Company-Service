package com.rentacar.repository;

import com.rentacar.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailEquals(String email);
    List<Employee> findAllByOrderBySecondName();
}
