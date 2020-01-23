package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.entity.ClientContactPerson;

public interface ClientContactPersonRepository extends JpaRepository<ClientContactPerson, Long> {
}
