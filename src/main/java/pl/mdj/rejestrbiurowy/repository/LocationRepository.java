package pl.mdj.rejestrbiurowy.repository;

import pl.mdj.rejestrbiurowy.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
