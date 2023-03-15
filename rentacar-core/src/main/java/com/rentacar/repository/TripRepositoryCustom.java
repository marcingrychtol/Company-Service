package com.rentacar.repository;

import com.rentacar.model.entity.Trip;

public interface TripRepositoryCustom {
    void detach(Trip trip);
}
