package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
