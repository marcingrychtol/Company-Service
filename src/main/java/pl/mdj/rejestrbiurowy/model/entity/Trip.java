package pl.mdj.rejestrbiurowy.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Employee employee;
    @ManyToOne
    @JoinColumn
    private Car car;
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private String additionalMessage;
    private Long mileage;

}


