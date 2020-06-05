package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private String registration;
    private boolean cancelled;
    private boolean available;
    private EmployeeDto occupier;
    private String fuel;
    private LocalDate insuranceExpiration;
    private LocalDate inspectionExpiration;
    private int mileage;

    private byte[] image;
    private String imageName;
    private String fileType;
}
