package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private String secondName;
    private String phoneNumber;
    private String email;
}
