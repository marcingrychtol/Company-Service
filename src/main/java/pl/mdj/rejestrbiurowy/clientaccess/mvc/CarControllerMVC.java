package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;

import java.io.InputStream;
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

    @GetMapping(path = "/manager")
    public String getCars(Model model) {
        model.addAttribute("active", "data");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("newCar", new CarDto());
        return ("manager/manager-cars");
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long carId) {

        CarDto car = new CarDto();
        try {
            car = carService.findById(carId);
        } catch (CannotFindEntityException ignored) {
        }

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(car.getImage(), headers, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public String editCar(@ModelAttribute CarDto carDto, Model model) {

        try {
            carService.update(carDto);
            model.addAttribute("successMessage", "Dane zmodyfikowane poprawnie!");
        } catch (EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getCars(model);
    }

    @PostMapping("/add")
    public String addCar(@ModelAttribute CarDto car, Model model) {
        try {
            carService.addOne(car);
            model.addAttribute("successMessage", "Pojazd dodano poprawnie!");
        } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getCars(model);
    }

    @PostMapping("/addimage/{id}")
    public String addPhoto(@RequestParam("image") MultipartFile img, @PathVariable("id") long carId, Model model) {
        try {
            carService.addPhoto(img, carId);
            model.addAttribute("successMessage", "Zdjęcie dodano poprawnie!");
        } catch (CannotFindEntityException | WrongInputDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getCars(model);
    }

    @PostMapping("/delete")
    public String deleteCar(@ModelAttribute CarDto carDto, Model model) {
        try {
            carService.deleteByDto(carDto);
            model.addAttribute("successMessage", "Pojazd usunięto!");
        } catch (WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("infoMessage", "Pojazd nie został usunięty, gdyż jest przypisany do rezerwacji, spróbuj opcji Wyłącz!");
        }

        return getCars(model);
    }

    @PostMapping("/cancel")
    public String cancelCar(@ModelAttribute CarDto carDto, Model model) {
        try {
            carService.cancelByDto(carDto);
            model.addAttribute("successMessage", "Pojazd wyłączono!");
        } catch (WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getCars(model);
    }

    @PostMapping("/enable")
    public String enableCar(@ModelAttribute CarDto carDto, Model model) {
        try {
            carService.enableByDto(carDto);
            model.addAttribute("successMessage", "Pojazd włączono!");
        } catch (CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getCars(model);
    }


}
