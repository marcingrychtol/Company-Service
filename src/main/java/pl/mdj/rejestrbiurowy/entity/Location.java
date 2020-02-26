package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Location implements MyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String country;
    private String city;
    private String street;
    private String parcelNumber;
}
