package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;
import pl.mdj.rejestrbiurowy.service.mappers.TripMapper;

import java.util.List;
import java.util.Objects;

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
        return tripMapper.mapToDto(tripRepository.findAll());
    }

    @Override
    public TripDto findOne(Long id) {
        return tripMapper.mapToDto(Objects
                .requireNonNull(tripRepository
                        .findById(id)
                        .orElse(null)));
    }

    @Override
    public TripDto addOne(TripDto tripDto) {
        tripRepository.save(tripMapper.mapToEntity(tripDto));
        return tripDto;
    }

    @Override
    public void deleteById(Long id) {
        tripRepository.deleteById(id);
    }
}
