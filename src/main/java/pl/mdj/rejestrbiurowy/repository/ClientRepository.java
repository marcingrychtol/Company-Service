package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
