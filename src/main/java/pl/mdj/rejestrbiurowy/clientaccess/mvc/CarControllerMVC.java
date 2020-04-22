package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "cars")
public class CarControllerMVC {

    private static final String REDIR_MAIN_CAR = "redirect:/cars";
    private static final String REDIR_MANAGER_CARS = "redirect:/cars/manager";
    Logger LOG = LoggerFactory.getLogger(CarControllerMVC.class);
    private CarService carService;
    private DateMapper dateMapper;

    @Autowired
    public CarControllerMVC(CarService carService, DateMapper dateMapper) {
        this.carService = carService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.getAllActive());
        model.addAttribute("newCar", new CarDto());
        return ("main/cars");
    }

    @GetMapping(path = "/manager")
    public String postEdit(Model model){
        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }

    @PostMapping("/edit")
    public String getAdd(@ModelAttribute CarDto carDto, Model model){

        try {
            carService.update(carDto);
            model.addAttribute("successMessage", "Dane zmodyfikowane poprawnie!");
        } catch (EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDto car, Model model){
        try {
            carService.addOne(car);
            model.addAttribute("successMessage", "Pojazd dodano poprawnie!");
        } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");    }

    @PostMapping("/delete")
    public String deleteCar(@ModelAttribute CarDto carDto, Model model){
        try {
            carService.deleteByDto(carDto);
            model.addAttribute("successMessage", "Pojazd usunięto!");
        } catch (WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e){
            carService.cancelByDto(carDto);
            model.addAttribute("infoMessage", "Pojazd nie został usunięty, oznaczono jako niedostępny do dalszej rezerwacji");
        }

        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }


}
