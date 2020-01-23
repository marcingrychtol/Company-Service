package pl.mdj.rejestrbiurowy.entity;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
