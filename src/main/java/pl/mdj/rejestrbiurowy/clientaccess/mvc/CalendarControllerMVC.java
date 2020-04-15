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
import pl.mdj.rejestrbiurowy.model.dto.DateDto;
import pl.mdj.rejestrbiurowy.model.dto.DayDto;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path ="/calendar")
public class CalendarControllerMVC {

    private Logger LOG = LoggerFactory.getLogger(CalendarControllerMVC.class);

    TripService tripService;
    CarService carService;
    EmployeeService employeeService;
    DateMapper dateMapper;
    DayService dayService;

    @Autowired
    public CalendarControllerMVC(TripService tripService, CarService carService, EmployeeService employeeService, DateMapper dateMapper, DayService dayService) {
        this.tripService = tripService;
        this.carService = carService;
        this.employeeService = employeeService;
        this.dateMapper = dateMapper;
        this.dayService = dayService;
    }

    @GetMapping("/browser")
    public String getCalendarBrowser(@ModelAttribute TripDto tripDto, @ModelAttribute DateDto dateDto, Model model) {

        LocalDate requestedDate;

        if (dateDto.getLocalDate() != null){
            requestedDate = dateDto.getLocalDate();
        } else if (tripDto.getStartingDate() != null){
            requestedDate = dateMapper.toLocalDate(tripDto.getStartingDate());
        } else {
            requestedDate = LocalDate.now();
        }

        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));
        model.addAttribute("tripDto", new TripDto());
        model.addAttribute("cars", carService.getAvailableCarsByDay(requestedDate));
        model.addAttribute("trips", tripService.findAllByDate(requestedDate));
        return "main/browser";
    }

    @GetMapping("{page}")
    public String getCalendar(Model model, @PathVariable int page) {

        model.addAttribute("page", page);
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("calendarPreview", getDataForIndexCalendarView(page, 24));
        model.addAttribute("cars", carService.getAllActive());
        model.addAttribute("tripDto", new TripDto());
        model.addAttribute("dateDto", new DateDto());
        return "main/calendar";
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


    private List<DayDto> getDataForIndexCalendarView(int page, int daysByPage){
        LocalDate start = LocalDate.now().plusDays(page*daysByPage);
        LocalDate end = start.plusDays(daysByPage-1);
        return dayService.getDaysDtoBetween(start, end);
    }
}
