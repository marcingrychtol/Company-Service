package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.service.interfaces.CarService;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;

@Controller
@RequestMapping("/")
public class ControllerMVC implements BasicController {

    EmployeeService employeeService;
    CarService carService;

    @Autowired
    public ControllerMVC(EmployeeService employeeService, CarService carService) {
        this.employeeService = employeeService;
        this.carService = carService;
    }

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        return "index";
    }
    

}
