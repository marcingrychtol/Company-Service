package pl.mdj.rejestrbiurowy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.model.entity.Trip;


public class TripRepositoryCustomImpl implements TripRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void detach(Trip trip) {
        em.detach(trip);
    }
}
