package pl.mdj.rejestrbiurowy.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;


@AllArgsConstructor
@RestController
@RequestMapping("trip")
public class TripController {
    /*
    * Trip
     */
    TripService tripService;


}
