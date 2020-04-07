package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
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
    public void addOne(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.mapToEntity(employeeDto));
    }

    @Override
    public void cancelByDto(EmployeeDto employeeDto) {
        employeeRepository.deleteById(employeeDto.getId());
    }

    @Override
    public void update(EmployeeDto employeeDto) throws EntityConflictException, WrongInputDataException {
        if (
                employeeDto.getEmail().length() < 5
                        || employeeDto.getName().length() < 5
                        || employeeDto.getSecondName().length() < 5
                        || employeeDto.getPhoneNumber().length() < 5
        ) {
            throw new WrongInputDataException("Weźże wprowadź dane dłuższe niż 5 znaków...");
        }

        Optional<Employee> empConflictTest = employeeRepository.findByEmailEquals(employeeDto.getEmail());
        if (empConflictTest.isPresent() && !empConflictTest.get().getId().equals(employeeDto.getId())) {
            throw new EntityConflictException(
                    "Pracownik o emailu "
                            + employeeDto.getEmail()
                            + " już istnieje! Jest to "
                            + empConflictTest.get().getName()
                            + " "
                            + empConflictTest.get().getSecondName()
            );
        }


        Optional<Employee> empOptional = employeeRepository.findById(employeeDto.getId());
        if (empOptional.isPresent()) {
            empOptional.get().setName(employeeDto.getName());
            empOptional.get().setSecondName(employeeDto.getSecondName());
            empOptional.get().setEmail(employeeDto.getEmail());
            employeeRepository.save(empOptional.get());
        }

    }

}
