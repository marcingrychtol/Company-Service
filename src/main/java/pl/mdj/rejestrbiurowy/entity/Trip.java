package pl.mdj.rejestrbiurowy.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private Long carId;
    private Long projectId;
    private Long locationId;
    private String description;
    private Long mileage;
/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public void setDescription(String description, Long mileage) {
        this.description = description;
        this.mileage = mileage;
    }

    public Trip() {
    }

    public Trip(Long id, String description) {
        this.id = id;
        this.description = description;
    }*/
}
