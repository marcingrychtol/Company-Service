package pl.mdj.rejestrbiurowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.entity.Day;
import pl.mdj.rejestrbiurowy.repository.DayRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;

import java.util.List;

@Service
@Transactional
public class DayServiceImpl implements DayService {

    TripRepository tripRepository;
    DayRepository dayRepository;

    @Autowired
    public DayServiceImpl(TripRepository tripRepository, DayRepository dayRepository) {
        this.tripRepository = tripRepository;
        this.dayRepository = dayRepository;
    }

    @Override
    public List<Day> getAll() {
        return null;
    }

    @Override
    public Day findById(Long id) throws CannotFindEntityException {
        return null;
    }

    @Override
    public Day addOne(Day day) throws EntityNotCompleteException, EntityConflictException {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
