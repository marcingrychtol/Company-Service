package pl.mdj.rejestrbiurowy.service.interfaces;

import pl.mdj.rejestrbiurowy.model.dto.TripDto;

import java.util.List;

public interface TripService extends BasicService<TripDto, Long> {
    public List<TripDto> findAllByEmployee_Id(Long id);
}


