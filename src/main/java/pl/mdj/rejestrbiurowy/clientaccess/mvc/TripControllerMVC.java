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
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    private Logger LOG = LoggerFactory.getLogger(TripControllerMVC.class);

    TripService tripService;
    EmployeeService employeeService;
    CarService carService;

    @Autowired
    public TripControllerMVC(TripService tripService, EmployeeService employeeService, CarService carService) {
        this.tripService = tripService;
        this.employeeService = employeeService;
        this.carService = carService;
    }

    //TODO: zrobić sortowanie po dacie

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
        model.addAttribute("employee", employeeService.findOne(id));
        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByEmployee_Id(id));

        addTripsMainSiteAttributesToModel(model);
        return "trips/trips";
    }

    @GetMapping("/car/{id}")
    public String getTripsFilteredByCar(@PathVariable("id") Long id, Model model){
        model.addAttribute("car", carService.findOne(id));
        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByCar_Id(id));

        addTripsMainSiteAttributesToModel(model);
        return "trips/trips";
    }

    @GetMapping("/day")
    public String getTripsFilteredByDay(@ModelAttribute TripDto tripDto, Model model){

        LocalDate date = LocalDate  // TODO - not the best way to do so
                .ofInstant(tripDto
                                .getStartingDate()
                                .toInstant(),
                        ZoneId.of("CET"));


        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByStartingDateEquals(date));
        addTripsMainSiteAttributesToModel(model);
        return "trips/trips";
    }

    @GetMapping("/filter")
    public String getTripsFiltered(@ModelAttribute TripDto tripDto, Model model){

        LocalDate date = LocalDate  // TODO - not the best way to do so
                .ofInstant(tripDto
                                .getStartingDate()
                                .toInstant(),
                        ZoneId.of("CET"));


        model.addAttribute("alertMessage", "Przeglądasz rezerwacje dla: ");
        model.addAttribute("trips", tripService.findAllByStartingDateEquals(date));
        return "trips/trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto, Model model){

        LOG.info(tripDto.getStartingDate().toString());

        if (tripDto.isComplete()){
            tripService.addOne(tripDto);
            return "redirect:/trips/";
        }

        model.addAttribute("errorMessage", "Rezerwacja nieskuteczna! Aby zarezerwować pojazd, musisz wypełnić wszystkie obowiązkowe pola.");
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        return "index";
    }

    @GetMapping("/edit")
    public String editTrips(Model model){
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

    @GetMapping("/delete/{id}")  // TODO: trzeba jakoś walidować usuwanie, bo wystarczy wpisać id do ścieżki -- np. przez wysłanie obiektu z formularza
    public String DeleteTrip(@PathVariable Long id){
        tripService.deleteById(id);
        return "redirect:/trips/edit";
    }

    private void addTripsMainSiteAttributesToModel(Model model){
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("tripDto", new TripDto());
    }

}
