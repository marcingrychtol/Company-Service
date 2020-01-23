package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ClientContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String name;
    private String phone;
    private String eMail;
}
