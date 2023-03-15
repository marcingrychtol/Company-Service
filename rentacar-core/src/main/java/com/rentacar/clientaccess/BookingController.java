package com.rentacar.clientaccess;

import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.model.DateFactory;
import com.rentacar.model.dto.BookingParamsDto;
import com.rentacar.model.dto.CarDto;
import com.rentacar.model.dto.DateDto;
import com.rentacar.model.dto.TripDto;
import com.rentacar.service.CarService;
import com.rentacar.service.DayService;
import com.rentacar.service.EmployeeService;
import com.rentacar.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/booking")
public class BookingController {

    Logger LOG = LoggerFactory.getLogger(BookingController.class);

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;
    final
    TripService tripService;
    DateFactory dateFactory;

    @Autowired
    public BookingController(EmployeeService employeeService, CarService carService, DayService dayService, DateFactory dateFactory, TripService tripService) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
        this.dateFactory = dateFactory;
        this.tripService = tripService;
    }


    /**
     * Entry point for Car-calendar
     *
     * @param bookingParams contains requested date, car model, scope
     * @param model
     * @return supplemented CarCalendarInfoDto
     */
    @GetMapping("/")
    public String getBookingByCar(@ModelAttribute(name = "bookingParams") BookingParamsDto bookingParams, Model model) {

        CarDto carDto = new CarDto();
        try {
            carDto = carService.findById(bookingParams.getCarId());
        } catch (CannotFindEntityException e) {
            LOG.warn("No car with ID: " + bookingParams.getCarId());
        }
        bookingParams.setCar(carDto);

        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("active", "booking");

        model.addAttribute("requestedDate", dateFactory.getDateDto(bookingParams.getRequestedDate()));
        model.addAttribute("bookingParams", bookingParams);
        model.addAttribute("requestParams", carService.fillBookingParamsDto(bookingParams));
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("dateDto", new DateDto());
        model.addAttribute("employees", employeeService.findAllActive());
        model.addAttribute("cars", carService.findAllActive());
        return "main/booking-car-calendar";
    }

    @GetMapping("/0")
    public String getFast(Model model) {

        LocalDate requestedDate = LocalDate.now();

        CarDto carDto = new CarDto();
        TripDto requestedTrip = new TripDto();
        requestedTrip.setCar(carDto);
        model.addAttribute("active", "booking");
        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateFactory.getDateDto(requestedDate));
        model.addAttribute("requestedTrip", requestedTrip);
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("employees", employeeService.findAllActive());
        model.addAttribute("cars", carService.findAllActive());
        return "main/booking";
    }
//
//    @InitBinder
//    public void customizeLocalDateBinder(WebDataBinder binder) {
//        // tell spring to set empty values as null instead of empty string.
//        binder.registerCustomEditor(LocalDate.class, new StringTrimmerEditor(true));
//        //The date format to parse or output your dates
//        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        //Create a new CustomDateEditor
//        CustomDateEditor localDateEditor = new CustomDateEditor(localDateFormat, true);
//        //Register it as custom editor for the Date type
//        binder.registerCustomEditor(LocalDate.class, localDateEditor);
//    }

}
