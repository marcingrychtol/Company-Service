package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.service.CarService;

@Controller
@RequestMapping(path = "cars")
public class CarControllerMVC {

    private static final String REDIR_MAIN_CAR = "redirect:/cars";
    private static final String REDIR_EDIT_CAR = "redirect:/cars/edit";
    Logger LOG = LoggerFactory.getLogger(CarControllerMVC.class);
    private CarService carService;

    @Autowired
    public CarControllerMVC(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public String getAll(Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("cars/cars");
    }

    @GetMapping(path = "/edit")
    public String getAdd(Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("cars/cars-edit");
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDto car){
        try {
            carService.addOne(car);
        } catch (EntityNotCompleteException | EntityConflictException e) {
            e.printStackTrace();
        }
        return REDIR_EDIT_CAR;
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable String id){
        carService.deleteById(Long.parseLong(id));
        return REDIR_EDIT_CAR;
    }


}
