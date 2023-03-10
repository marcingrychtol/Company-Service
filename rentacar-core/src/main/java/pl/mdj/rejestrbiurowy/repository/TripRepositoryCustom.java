package pl.mdj.rejestrbiurowy.repository;

import pl.mdj.rejestrbiurowy.model.entity.Trip;

public interface TripRepositoryCustom {
    void detach(Trip trip);
}
