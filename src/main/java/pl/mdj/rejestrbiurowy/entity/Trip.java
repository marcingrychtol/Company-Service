package pl.mdj.rejestrbiurowy.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Trip implements MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Employee employee;
    @ManyToOne
    @JoinColumn
    private Car car;
    @ManyToOne
    @JoinColumn
    private Project project;
    @ManyToOne
    @JoinColumn
    private Location location;
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private String description;
    private Long mileage;

}


