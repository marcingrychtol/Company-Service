package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.service.CarService;

@Controller
@RequestMapping(path = "cars")
public class CarControllerMVC {

    private static final String REDIR_MAIN_CAR = "redirect:/cars";
    private static final String REDIR_MANAGER_CARS = "redirect:/cars/manager";
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
        return ("main/cars");
    }

    @GetMapping(path = "/manager")
    public String postEdit(@ModelAttribute CarDto carDto, Model model){

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }

    @PostMapping("/edit")
    public String getAdd(@ModelAttribute CarDto carDto, Model model){

        try {
            carService.update(carDto);
        } catch (EntityConflictException | WrongInputDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("name", "Rezerwator");
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDto car, Model model){
        try {
            carService.addOne(car);
        } catch (EntityNotCompleteException | EntityConflictException e) {
            e.printStackTrace();
        }
        return REDIR_MANAGER_CARS;
    }

    @PostMapping("/delete")
    public String deleteCar(@PathVariable String id){
        carService.cancelById(Long.parseLong(id));
        return REDIR_MANAGER_CARS;
    }


}
