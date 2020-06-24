package pl.mdj.rejestrbiurowy.repository;

import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.model.entity.Trip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TripRepositoryCustomImpl implements TripRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void detach(Trip trip) {
        em.detach(trip);
    }
}
