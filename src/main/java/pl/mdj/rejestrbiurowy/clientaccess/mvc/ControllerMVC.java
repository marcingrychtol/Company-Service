package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.clientaccess.dto.TripDto;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.entity.Trip;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class ControllerMVC implements BasicController {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerMVC.class);
    EmployeeService employeeService;
    CarService carService;

    @Autowired
    public ControllerMVC(EmployeeService employeeService, CarService carService) {
        this.employeeService = employeeService;
        this.carService = carService;
    }

    @GetMapping("/")
    public String getHome(Model model){
        try{
            model.getAttribute("day");
        } catch (Exception e){
            LOG.info(e.getMessage());
        }

        model.addAttribute("alert", "testowy alert");
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        return "index";
    }

    @GetMapping("/datepicker")
    public String getDatepicker(){
        return "datepicker";
    }

}
