package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerMVC implements BasicController {

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;

    @Autowired
    public ControllerMVC(EmployeeService employeeService, CarService carService, DayService dayService) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
    }

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("calendarPreview", getDataForIndexCalendarView());
        return "index";
    }

    private List<DayDto> getDataForIndexCalendarView(){
        LocalDate start = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate end = start.plusDays(28);
        return dayService.getDaysDtoBetween(start, end);
    }
    

}
