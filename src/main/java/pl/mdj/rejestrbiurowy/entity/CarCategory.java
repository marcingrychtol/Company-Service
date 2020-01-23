package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class CarCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private ECarCategory category;

    @OneToMany(mappedBy = "car_category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    public void addCar(Car car){
        getCars().add(car);
        car.setCarCategory(this);
    }
}
