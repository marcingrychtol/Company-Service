package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import pl.mdj.rejestrbiurowy.entity.enums.EEmployeeCategory;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Employee author;
    @ManyToOne
    @JoinColumn(name = "addressee_id")
    private Employee addressee;
    private EEmployeeCategory addresseeCategory;
    private String subjectTableName;
    private String subjectItemId;
    private String description;
    private Boolean active;
}
