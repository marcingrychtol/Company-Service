package pl.mdj.rejestrbiurowy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.repository.LocationRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.LocationService;

@AllArgsConstructor
@RestController
@RequestMapping("location")
public class LocationController {
    /*
    * Location
     */

    LocationService locationService;
}
