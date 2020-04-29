package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.model.mappers.EmployeeMapper;

import java.util.ArrayList;
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
    public List<EmployeeDto> findAll() {
        return employeeMapper.mapToDto(employeeRepository.findAll());
    }

    @Override
    public List<EmployeeDto> findAllActive() {
        List<EmployeeDto> dtoList = findAll();
        List<EmployeeDto> dtoListActive = new ArrayList<>();
        for (EmployeeDto emp :
                dtoList) {
            if (!emp.isCancelled()) {
                dtoListActive.add(emp);
            }
        }
        return dtoListActive;
//        return getAll().stream()
//                .filter(emp -> !emp.getCancelled())
//                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(Long id) throws CannotFindEntityException {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            return employeeMapper.mapToDto(optional.get());
        } else {
            throw new CannotFindEntityException("Cannot find employee of id: " + id);
        }
    }

    @Override
    public void addOne(EmployeeDto employeeDto) throws WrongInputDataException, EntityConflictException {
        checkInputLengthData(employeeDto); // throws WIDE
        checkDuplicates(employeeDto); // throws ECE
        employeeRepository.save(employeeMapper.mapToEntity(employeeDto));
    }

    @Override
    public void cancelByDto(EmployeeDto employeeDto) throws CannotFindEntityException {
        Optional<Employee> empOptional = employeeRepository.findById(employeeDto.getId());
        if (!empOptional.isPresent()){
            throw new CannotFindEntityException("Pracownik nie istnieje, wystąpił błąd! (jednoczesna edycja z innego stanowiska)");
        }
        Employee employee = empOptional.get();
        employee.setCancelled(true);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteByDto(EmployeeDto employeeDto) throws WrongInputDataException, CannotFindEntityException, DataIntegrityViolationException {
        Optional<Employee> empOptional = employeeRepository.findById(employeeDto.getId());
        if (!empOptional.isPresent()){
            throw new CannotFindEntityException("Pracownik nie istnieje, wystąpił błąd! (jednoczesna edycja z innego stanowiska)");
        }
        if (!empOptional.get().getEmail().equals(employeeDto.getEmail())) {
            throw new WrongInputDataException("Niepoprawne dane, nie można usunąć pracownika!");
        }
        employeeRepository.deleteById(employeeDto.getId());
    }

    @Override
    public void enableByDto(EmployeeDto employeeDto) throws CannotFindEntityException {
        Optional<Employee> empOptional = employeeRepository.findById(employeeDto.getId());
        if (!empOptional.isPresent()){
            throw new CannotFindEntityException("Pracownik nie istnieje, wystąpił błąd! (jednoczesna edycja z innego stanowiska)");
        }
        Employee employee = empOptional.get();
        employee.setCancelled(false);
        employeeRepository.save(employee);
    }

    @Override
    public void update(EmployeeDto employeeDto) throws EntityConflictException, WrongInputDataException, CannotFindEntityException {

        checkInputLengthData(employeeDto);
        checkDuplicates(employeeDto);

        Optional<Employee> empOptional = employeeRepository.findById(employeeDto.getId());
        if (empOptional.isPresent()) {
            empOptional.get().setName(employeeDto.getName());
            empOptional.get().setSecondName(employeeDto.getSecondName());
            empOptional.get().setEmail(employeeDto.getEmail());
            employeeRepository.save(empOptional.get());
        } else {
            throw new CannotFindEntityException(
                    "Nie można wprowadzić danych, pracownik nie istnieje lub jest nieaktywny. " +
                            "Prawdopodobnie ktoś zmienił dane w międzyczasie");
        }

    }


    private void checkInputLengthData(EmployeeDto employeeDto) throws WrongInputDataException {
        if (
                employeeDto.getEmail().length() < 5
                        || employeeDto.getName().length() < 3
                        || employeeDto.getSecondName().length() < 5
                        || employeeDto.getPhoneNumber().length() < 5
        ) {
            throw new WrongInputDataException("Weźże wprowadź dane dłuższe niż 5 znaków...");
        }
    }

    private void checkDuplicates(EmployeeDto employeeDto) throws EntityConflictException {
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
    }

}
