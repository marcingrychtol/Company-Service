package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;

@Controller
@RequestMapping(path = "cars")
public class CarControllerMVC {

    private static final String REDIR_MAIN_CAR = "redirect:/mvc/car/all";
    private static final String REDIR_ADD_CAR = "redirect:/mvc/car/add";
    Logger LOG = LoggerFactory.getLogger(CarControllerMVC.class);
    private CarService carService;

    @Autowired
    public CarControllerMVC(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(path = "/all")
    public String getAll(Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new Car());
        return ("cars");
    }

    @GetMapping(path = "/add")
    public String getAdd(Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new Car());
        return ("car-add");
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute Car car){
        carService.addOne(car);
        return REDIR_ADD_CAR;
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable String id){
        carService.deleteById(Long.parseLong(id));
        return REDIR_MAIN_CAR;
    }
}
