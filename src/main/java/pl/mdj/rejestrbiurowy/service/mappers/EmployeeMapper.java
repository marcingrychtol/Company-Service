package pl.mdj.rejestrbiurowy.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.entity.Car;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;

import java.util.List;
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
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSecondName(entity.getSecondName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    @Override
    public List<EmployeeDto> mapToDto(List<Employee> entityList) {
        return entityList.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
    }

    @Override
    public Employee mapToEntity(EmployeeDto dto) {
        Employee entity;

        if (dto.getId() != null) {
            entity = employeeRepository.findById(dto.getId()).orElse(new Employee());
            if (entity.getId() != null) {
                return entity;
            }
        }

        entity = new Employee();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSecondName(dto.getSecondName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    @Override
    public List<Employee> mapToEntity(List<EmployeeDto> dtoList) {
        return dtoList.stream().map(d -> mapToEntity(d)).collect(Collectors.toList());
    }
}
