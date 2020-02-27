package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
