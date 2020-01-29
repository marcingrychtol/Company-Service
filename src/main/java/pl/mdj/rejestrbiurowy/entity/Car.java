package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Car extends MyEntity {

    @ManyToOne
    @JoinColumn(name = "car_category_id")
    private CarCategory carCategory;
    private String name;
    private String registration;
    private String fuel;
    private LocalDate insuranceExpiration;
    private LocalDate inspectionExpiration;
    private int mileage;
}
