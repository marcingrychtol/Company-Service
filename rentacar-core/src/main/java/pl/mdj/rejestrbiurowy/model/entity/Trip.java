package pl.mdj.rejestrbiurowy.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "car_id")
    private Car car;
    private Boolean cancelled = false;

    private LocalDate startingDate;
    private LocalDate endingDate;
    private String additionalMessage;

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private LocalDateTime cancelledTime;

    @ManyToMany(mappedBy = "trips", cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.DETACH, CascadeType.REFRESH})
//    @ManyToMany(mappedBy = "trips", cascade = {CascadeType.ALL})
    private List<Day> days;

    public List<Day> getDays() {
        if (days == null){
            days = new ArrayList<>();
        }
        return days;
    }

}


