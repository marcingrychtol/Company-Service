package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import pl.mdj.rejestrbiurowy.entity.enums.ETaskCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ETaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private Long employeeId;
    @Enumerated(EnumType.STRING)
    private ETaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    private ETaskCategory taskCategory;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;

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
