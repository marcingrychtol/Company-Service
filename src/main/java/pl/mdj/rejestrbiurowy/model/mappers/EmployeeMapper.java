package pl.mdj.rejestrbiurowy.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper implements BasicMapper<Employee, EmployeeDto> {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto mapToDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();

        Optional.ofNullable(entity.getId()).ifPresent(dto::setId);
        Optional.ofNullable(entity.getName()).ifPresent(dto::setName);
        Optional.ofNullable(entity.getSecondName()).ifPresent(dto::setSecondName);
        Optional.ofNullable(entity.getEmail()).ifPresent(dto::setEmail);
        Optional.ofNullable(entity.getPhoneNumber()).ifPresent(dto::setPhoneNumber);

        return dto;
    }

    @Override
    public List<EmployeeDto> mapToDto(List<Employee> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Employee mapToEntity(EmployeeDto dto) {
        Employee entity = new Employee();

        Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
        Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
        Optional.ofNullable(dto.getSecondName()).ifPresent(entity::setSecondName);
        Optional.ofNullable(dto.getPhoneNumber()).ifPresent(entity::setPhoneNumber);

        return entity;
    }

    @Override
    public List<Employee> mapToEntity(List<EmployeeDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
