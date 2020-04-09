package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

@Data
public class CarDto {
    private Long id;
    private String name;
    private String registration;
    private Boolean cancelled;
    private Boolean available;
//    private String fuel;
//    private LocalDate insuranceExpiration;
//    private LocalDate inspectionExpiration;
//    private int mileage;
}
