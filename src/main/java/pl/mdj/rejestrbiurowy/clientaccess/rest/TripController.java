package pl.mdj.rejestrbiurowy.clientaccess.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;


@AllArgsConstructor
@RestController
@RequestMapping("api/trip")
public class TripController {
    /*
    * Trip
     */
    TripService tripService;


}
