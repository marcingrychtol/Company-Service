package pl.mdj.rejestrbiurowy.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String name;
    private String description;
    private Boolean active;

    public Project(Client client, String name, String description) {
        this.client = client;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public void addTask(Task task){
        getTasks().add(task);
        task.setProject(this);
    }
}



