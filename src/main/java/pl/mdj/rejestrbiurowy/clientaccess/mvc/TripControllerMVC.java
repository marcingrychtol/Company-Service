package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.clientaccess.dto.TripDto;
import pl.mdj.rejestrbiurowy.entity.Trip;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    private static Logger LOG = LoggerFactory.getLogger(TripControllerMVC.class);

    TripService tripService;
    CarService carService;
    EmployeeService employeeService;

    @Autowired
    public TripControllerMVC(TripService tripService, CarService carService, EmployeeService employeeService) {
        this.tripService = tripService;
        this.carService = carService;
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public String getCalendar(Model model){
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto){
        Trip trip = new Trip();
        trip.setCar(carService.getOne(tripDto.getCarId()));
        trip.setEmployee(employeeService.getOne(tripDto.getEmployeeId()));
        LOG.warn(tripDto.getDate().toString());
//        trip.setDate(LocalDate.of(tripDto.getDate()));
        tripService.addOne(trip);
        return "redirect:/trips/";
    }


}
