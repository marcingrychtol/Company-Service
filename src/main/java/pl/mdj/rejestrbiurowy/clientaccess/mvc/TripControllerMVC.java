package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.Employee;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;
import pl.mdj.rejestrbiurowy.service.mappers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    private Logger LOG = LoggerFactory.getLogger(TripControllerMVC.class);

    TripService tripService;
    EmployeeService employeeService;
    CarService carService;
    DateMapper dateMapper;

    @Autowired
    public TripControllerMVC(TripService tripService, EmployeeService employeeService, CarService carService, DateMapper dateMapper) {
        this.tripService = tripService;
        this.employeeService = employeeService;
        this.carService = carService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("")
    public String getAllTrips(Model model){
        model.addAttribute("tripDto", new TripDto());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips";
    }

    @GetMapping("/employee/{id}")
    public String getTripsFilteredByEmployee(@PathVariable("id") Long id, Model model){
        EmployeeDto employee;
        try {
            employee = employeeService.findById(id);
            model.addAttribute("employee", employee);
        } catch (CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByEmployee_Id(id));

        addTripsMainSiteAttributesToModel(model);
        return "trips/trips";
    }

    @GetMapping("/car/{id}")
    public String getTripsFilteredByCar(@PathVariable("id") Long id, Model model){
        CarDto carDto;
        try {
            carDto = carService.findById(id);
            model.addAttribute("car", carDto);
        } catch (CannotFindEntityException e) {
            LOG.info(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByCar_Id(id));

        addTripsMainSiteAttributesToModel(model);
        return "trips/trips";
    }

    @GetMapping("/filter")
    public String getTripsFiltered(@ModelAttribute TripDto tripDto, Model model){

        Optional<Date> optionalDate = Optional.ofNullable(tripDto.getStartingDate());
        LocalDate date = dateMapper.toLocalDate(optionalDate.orElse(new Date()));

        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByStartingDateEquals(date));
        return "trips/trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto, Model model){

        LOG.info(tripDto.getStartingDate().toString());
        LocalDate requestedDate = dateMapper.toLocalDate(tripDto.getStartingDate());

        try {
            tripService.addOne(tripDto);
            model.addAttribute("successMessage","Rezerwacja dodana poprawnie!");
        } catch (EntityNotCompleteException | EntityConflictException e) {
            LOG.info(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("today", LocalDate.now());
        model.addAttribute("todayFullDayPL", dateMapper.dayOfWeekPL(LocalDate.now()));
        model.addAttribute("year", requestedDate.getYear());
        model.addAttribute("month", requestedDate.getMonthValue());
        model.addAttribute("day", requestedDate.getDayOfMonth());
        model.addAttribute("tripDto", new TripDto());
        model.addAttribute("cars", carService.getAvailable(requestedDate));
        model.addAttribute("trips", tripService.findAllByStartingDateEquals(requestedDate));

        return "calendar/calendar";
    }

    @GetMapping("/edit")
    public String editTrips(Model model){
        model.addAttribute("deleteTrip", new TripDto());
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips-edit";
    }

    @InitBinder
    public void customizeDateBinder( WebDataBinder binder )
    {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( Date.class, new StringTrimmerEditor( true ));

        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @PostMapping("/delete")
    public String deleteTrip(@ModelAttribute TripDto tripDto){
        tripService.deleteById(tripDto.getId());
        return "redirect:/trips/edit";
    }

    private void addTripsMainSiteAttributesToModel(Model model){
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("tripDto", new TripDto());
    }

}
