package pl.mdj.rejestrbiurowy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;
@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {
}
