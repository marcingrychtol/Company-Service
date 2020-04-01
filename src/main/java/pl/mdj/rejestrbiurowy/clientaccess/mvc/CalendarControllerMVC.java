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
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping(path ="/calendar")
public class CalendarControllerMVC {

    private Logger LOG = LoggerFactory.getLogger(CalendarControllerMVC.class);

    TripService tripService;
    CarService carService;
    EmployeeService employeeService;
    DateMapper dateMapper;

    @Autowired
    public CalendarControllerMVC(TripService tripService, CarService carService, EmployeeService employeeService, DateMapper dateMapper) {
        this.tripService = tripService;
        this.carService = carService;
        this.employeeService = employeeService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("")
    public String getCalendar(@ModelAttribute TripDto tripDto, Model model) {

        LocalDate requestedDate;
        if (tripDto.getStartingDate() != null) {
            requestedDate = dateMapper.toLocalDate(tripDto.getStartingDate());
        } else {
            requestedDate = LocalDate.now();
        }

        model.addAttribute("today", LocalDate.now());
        model.addAttribute("todayFullDayPL", dateMapper.dayOfWeekPL(LocalDate.now()));
        model.addAttribute("year", requestedDate.getYear());
        model.addAttribute("month", dateMapper.valueWithZeroForJS(requestedDate.getMonthValue()));
        model.addAttribute("day", dateMapper.valueWithZeroForJS(requestedDate.getDayOfMonth()));
        model.addAttribute("tripDto", new TripDto());
        model.addAttribute("cars", carService.getAvailable(requestedDate));
        model.addAttribute("trips", tripService.findAllByDate(requestedDate));
        return "calendar/calendar";
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


}
