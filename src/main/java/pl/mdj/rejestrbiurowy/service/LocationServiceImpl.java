package pl.mdj.rejestrbiurowy.service;

import org.springframework.stereotype.Service;
import pl.mdj.rejestrbiurowy.service.interfaces.LocationService;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
}
