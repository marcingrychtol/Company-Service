package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

List<Trip> findAllByEmployee_Id(Long id);

List<Trip> findAllByCar_Id(Long id);

List<Trip> findAllByStartingDateEquals(LocalDate date);
List<Trip> findAllByStartingDateIsNot(LocalDate date);

//    @EntityGraph(value = "Trip")
}
