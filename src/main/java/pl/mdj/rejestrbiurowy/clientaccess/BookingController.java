package pl.mdj.rejestrbiurowy.clientaccess;

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
import pl.mdj.rejestrbiurowy.model.dto.BookingParamsDto;
import pl.mdj.rejestrbiurowy.model.dto.CarDto;
import pl.mdj.rejestrbiurowy.model.dto.DateDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/booking")
public class BookingController {

    Logger LOG = LoggerFactory.getLogger(BookingController.class);

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;
    DateMapper dateMapper;

    @Autowired
    public BookingController(EmployeeService employeeService, CarService carService, DayService dayService, DateMapper dateMapper) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
        this.dateMapper = dateMapper;
    }


    /**
     * Entry point for Car-calendar
     *
     * @param bookingParams contains requested date, car model, scope
     * @param model
     * @return supplemented CarCalendarInfoDto
     */
    @GetMapping
    public String getBookingByCar(@ModelAttribute(name="bookingParams") BookingParamsDto bookingParams, Model model){

        CarDto carDto = new CarDto();
        try {
            carDto = carService.findById(bookingParams.getCarId());
        } catch (CannotFindEntityException e) {
            LOG.warn("No car with ID: " + bookingParams.getCarId());
        }

        bookingParams.setCar(carDto);

        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("active", "booking");

        model.addAttribute("requestedDate", dateMapper.getDateDto(bookingParams.getRequestedDate()));
        model.addAttribute("bookingParams", bookingParams);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("dateDto", new DateDto());
        model.addAttribute("employees", employeeService.findAllActive());
        model.addAttribute("cars", carService.findAllActive());
        model.addAttribute("carCalendarInfo", carService.getCarCalendarInfo(bookingParams));
        return "main/booking-car-calendar";
    }

    @GetMapping("/0")
    public String getFast(Model model){

        LocalDate requestedDate = LocalDate.now();

        CarDto carDto = new CarDto();
        TripDto requestedTrip = new TripDto();
        requestedTrip.setCar(carDto);
        model.addAttribute("active", "booking");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));
        model.addAttribute("requestedTrip", requestedTrip);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.findAllActive());
        model.addAttribute("cars", carService.findAllActive());
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
