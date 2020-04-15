package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDto {
    private Long id;
    private String name;
    private String registration;
    private boolean cancelled;
    private boolean available;
    private String fuel;
    private LocalDate insuranceExpiration;
    private LocalDate inspectionExpiration;
    private int mileage;
}
