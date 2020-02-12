package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CarCategory extends MyEntity {

    @Enumerated(EnumType.STRING)
    private ECarCategory category;

    @OneToMany(mappedBy = "carCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    public void addCar(Car car){
        getCars().add(car);
        car.setCarCategory(this);
    }
}