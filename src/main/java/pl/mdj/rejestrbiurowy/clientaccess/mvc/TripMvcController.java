package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ECarFuel;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "mvc")
public class TripMvcController {

    @GetMapping(path = "/car")
    public String get(Model model){

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();

        car1.setCarCategory(ECarCategory.COMPANY);
        car1.setFuel(ECarFuel.BENZYNA);
        car1.setMileage(270253);
        car1.setName("Dacia Dokker");
        car1.setRegistration("SL 1404");

        car2.setCarCategory(ECarCategory.PRIVATE_BIG);
        car2.setFuel(ECarFuel.BENZYNA);
        car2.setMileage(30000);
        car2.setName("Skoda Octavia");
        car2.setRegistration("NN 8524");

        car3.setCarCategory(ECarCategory.PRIVATE_SMALL);
        car3.setFuel(ECarFuel.DIESEL);
        car3.setMileage(300000);
        car3.setName("Audi A4");
        car3.setRegistration("WZ 4569");

        car4.setCarCategory(ECarCategory.COMPANY);
        car4.setFuel(ECarFuel.DIESEL);
        car4.setMileage(180500);
        car4.setName("VW LT");
        car4.setRegistration("KT 7777");

        List<Car> cars = Arrays.asList(car1,car2, car3, car4);

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", cars);
        return ("car");
    }
}
