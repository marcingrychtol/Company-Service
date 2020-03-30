package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.model.mappers.EmployeeMapper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeMapper.mapToDto(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto findById(Long id) throws CannotFindEntityException{
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()){
            return employeeMapper.mapToDto(optional.get());
        } else {
            throw new CannotFindEntityException("Cannot find employee of id: " + id);
        }
    }

    @Override
    public EmployeeDto addOne(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.mapToEntity(employeeDto));
        return employeeDto;
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

}
