package pl.mdj.rejestrbiurowy.clientaccess.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;

@AllArgsConstructor
@RestController
@RequestMapping("car")
public class CarController {
    /*
     * Car
     * CarCategory
     */

    CarService carService;

}
