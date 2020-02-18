package pl.mdj.rejestrbiurowy.service.interfaces;

import org.springframework.stereotype.Service;
import pl.mdj.rejestrbiurowy.entity.Employee;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;

public interface EmployeeService extends BasicService<Employee,Long>{
}
