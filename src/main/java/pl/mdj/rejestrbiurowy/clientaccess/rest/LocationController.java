package pl.mdj.rejestrbiurowy.clientaccess.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
