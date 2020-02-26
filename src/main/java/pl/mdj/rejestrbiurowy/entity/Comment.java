package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import pl.mdj.rejestrbiurowy.entity.enums.EmployeeCategory;

import javax.persistence.*;

@Entity
@Data
public class Comment implements MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Employee author;
    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private Employee addressee;
    private EmployeeCategory addresseeCategory;
    private String subjectTableName;
    private String subjectItemId;
    private String description;
    private Boolean active;
}
