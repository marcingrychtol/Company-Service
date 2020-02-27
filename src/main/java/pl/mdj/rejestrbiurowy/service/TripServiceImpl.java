package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Trip;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;
import pl.mdj.rejestrbiurowy.service.mappers.TripMapper;

import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements TripService {


    TripRepository tripRepository;
    TripMapper tripMapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public List<TripDto> getAll() {
        return null;
    }

    @Override
    public TripDto findOne(Long id) {
        return null;
    }

    @Override
    public TripDto addOne(TripDto tripDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
