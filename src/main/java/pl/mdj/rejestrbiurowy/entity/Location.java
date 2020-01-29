package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Location extends MyEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String country;
    private String city;
    private String street;
    private String parcelNumber;
}
