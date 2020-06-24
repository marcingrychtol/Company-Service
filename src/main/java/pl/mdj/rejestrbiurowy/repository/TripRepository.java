package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.model.entity.Trip;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    List<Trip> findByOrderByStartingDateAsc();
    List<Trip> findByOrderByStartingDateDesc();
    List<Trip> findAll(Sort sort);

    List<Trip> findAllByEmployee_IdOrderByStartingDateDesc(Long id);
    List<Trip> findAllByCar_IdOrderByStartingDateDesc(Long id);
    List<Trip> findAllByAdditionalMessageContainingIgnoreCase(String search);
    //    @EntityGraph(value = "Trip")
}
