package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/")
public class BookingControllerMVC implements BasicController {

    Logger LOG = LoggerFactory.getLogger(BookingControllerMVC.class);

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;
    DateMapper dateMapper;

    @Autowired
    public BookingControllerMVC(EmployeeService employeeService, CarService carService, DayService dayService, DateMapper dateMapper) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("/")
    public String getHome(Model model){
        LocalDate requestedDate = LocalDate.now();

        CarDto carDto = new CarDto();
        TripDto requestedTrip = new TripDto();
        requestedTrip.setCar(carDto);
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));
        model.addAttribute("requestedTrip", requestedTrip);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("cars", carService.getAllActive());
        return "main/booking";
    }

    @GetMapping("/booking")
    public String getHomeBooking(@ModelAttribute TripDto tripDto, Model model){

        CarDto carDto = new CarDto();
        try {
            carDto = carService.findById(tripDto.getCarId());
        } catch (CannotFindEntityException e) {
            LOG.warn("No car with ID: " + tripDto.getCarId());
        }

        tripDto.setCar(carDto);

        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("requestedDate", dateMapper.getDateDto(dateMapper.toLocalDate(tripDto.getStartingDate())));
        model.addAttribute("requestedTrip", tripDto);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("cars", carService.getAllActive());
        return "main/booking";
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
