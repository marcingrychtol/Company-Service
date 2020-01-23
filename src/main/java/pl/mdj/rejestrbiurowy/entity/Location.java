package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String country;
    private String city;
    private String street;
    private String parcelNumber;
}
