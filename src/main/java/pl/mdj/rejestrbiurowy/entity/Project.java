package pl.mdj.rejestrbiurowy.entity;

import lombok.*;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Project extends MyEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String name;
    private String description;
    private Boolean active;

    public Project(Client client, String name, String description) {
        this.client = client;
    }

//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Task> tasks;


}



