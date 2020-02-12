package pl.mdj.rejestrbiurowy.entity.interfaces;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
}
