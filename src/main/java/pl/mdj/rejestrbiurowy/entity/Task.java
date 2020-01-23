package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import pl.mdj.rejestrbiurowy.entity.enums.ETaskCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ETaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Enumerated(EnumType.STRING)
    private ETaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    private ETaskCategory taskCategory;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<>();
        }
        return employees;
    }

    @ManyToMany(mappedBy = "employees", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Employee> employees;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Task() {
//    }
//
//    public Task(String description) {
//        this.description = description;
//    }
}
