package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mdj.rejestrbiurowy.entity.enums.EEmployeeCategory;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends MyEntity {

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
