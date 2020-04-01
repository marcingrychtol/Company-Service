package pl.mdj.rejestrbiurowy.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    private Boolean cancelled = false; // TODO: implement

    private LocalDate startingDate;
    private LocalDate endingDate;
    private String additionalMessage;

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private LocalDateTime cancelledTime;

    @ManyToMany(mappedBy = "trips", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Day> days;

    public List<Day> getDays() {
        if (days == null){
            days = new ArrayList<>();
        }
        return days;
    }
}


