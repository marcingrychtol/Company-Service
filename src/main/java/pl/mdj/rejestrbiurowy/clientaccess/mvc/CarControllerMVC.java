package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.entity.CarSimple;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ECarFuel;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "mvc/car")
public class CarControllerMVC {

    Logger LOG = LoggerFactory.getLogger(CarControllerMVC.class);

    private CarService carService;

    public CarControllerMVC(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(path = "/all")
    public String get(Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("newCar", new Car());
        return ("car");
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car){
        carService.addCar(car);
        LOG.info("car added ", car);
        return "redirect:/mvc/car/all";
    }
}
