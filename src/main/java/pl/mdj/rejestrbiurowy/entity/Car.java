package pl.mdj.rejestrbiurowy.entity;

import lombok.*;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ECarFuel;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Car extends MyEntity {

    @Enumerated(EnumType.STRING)
    private ECarCategory carCategory;
    private String name;
    private String registration;
    @Enumerated(EnumType.STRING)
    private ECarFuel fuel;
    private LocalDate insuranceExpiration;
    private LocalDate inspectionExpiration;
    private int mileage;

    public Car(String name, String registration) {
        this.name = name;
        this.registration = registration;
    }
}
