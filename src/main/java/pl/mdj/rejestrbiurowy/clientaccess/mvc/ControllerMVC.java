package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerMVC implements BasicController {

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;
    DateMapper dateMapper;

    @Autowired
    public ControllerMVC(EmployeeService employeeService, CarService carService, DayService dayService, DateMapper dateMapper) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("/")
    public String getHome(Model model){
        LocalDate requestedDate = LocalDate.now();
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));
        model.addAttribute("requestedTrip", new TripDto());
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        return "index";
    }

    @GetMapping("/booking")
    public String getHomeBooking(@ModelAttribute TripDto tripDto, Model model){
        model.addAttribute("requestedDate", dateMapper.getDateDto(dateMapper.toLocalDate(tripDto.getStartingDate())));
        model.addAttribute("requestedTrip", tripDto);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("cars", carService.getAll());
        return "index";
    }


    @InitBinder
    public void customizeDateBinder( WebDataBinder binder )
    {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( Date.class, new StringTrimmerEditor( true ));
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Create a new CustomDateEditor
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, dateEditor);
    }

}
