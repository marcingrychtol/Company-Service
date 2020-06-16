package pl.mdj.rejestrbiurowy.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private String registration;
    private boolean cancelled;
    private boolean available = true;
    private EmployeeDto occupier;
    private String fuel;
    private LocalDate insuranceExpiration;
    private LocalDate inspectionExpiration;
    private int mileage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return mileage == carDto.mileage &&
                Objects.equals(id, carDto.id) &&
                Objects.equals(brand, carDto.brand) &&
                Objects.equals(model, carDto.model) &&
                Objects.equals(registration, carDto.registration) &&
                Objects.equals(fuel, carDto.fuel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, registration, fuel, mileage);
    }

    private byte[] image;
    private String imageName;
    private String fileType;
}
