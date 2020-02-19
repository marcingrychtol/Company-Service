package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;

import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> getAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getOne(Long id) {
        return tripRepository.getOne(id);
    }

    @Override
    public Trip addOne(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void deleteById(Long id) {
        tripRepository.deleteById(id);
    }
}
