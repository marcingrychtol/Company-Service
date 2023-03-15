package com.rentacar.repository;

import com.rentacar.model.entity.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;


public class TripRepositoryCustomImpl implements TripRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void detach(Trip trip) {
        em.detach(trip);
    }
}
