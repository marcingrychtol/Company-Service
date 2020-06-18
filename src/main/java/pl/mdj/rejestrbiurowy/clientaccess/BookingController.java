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
import pl.mdj.rejestrbiurowy.model.dto.*;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.DayService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    Logger LOG = LoggerFactory.getLogger(BookingController.class);

    EmployeeService employeeService;
    CarService carService;
    DayService dayService;
    final
    TripService tripService;
    DateMapper dateMapper;

    @Autowired
    public BookingController(EmployeeService employeeService, CarService carService, DayService dayService, DateMapper dateMapper, TripService tripService) {
        this.employeeService = employeeService;
        this.carService = carService;
        this.dayService = dayService;
        this.dateMapper = dateMapper;
        this.tripService = tripService;
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

    @PostMapping("/resolve")
    public String postBookingForm(@ModelAttribute(name = "bookingParams") CarCalendarInfoDto carCalendarInfoDto, Model model){

        model.addAttribute("active", "booking");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        List<TripDto> resolvedTrips = joinRequestedTrips(carCalendarInfoDto);
        List<TripDto> conflictedTrips = tripService.findConflictedTrips(resolvedTrips);
        model.addAttribute("conflicts", conflictedTrips);


        LocalDate requestedDate = LocalDate.now();
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));

        return "main/booking-conflict-confirm";

    }


    /**
     * Used to find all trips around request
     *
     *
     * @param carCalendarInfoDto
     * @return
     */
    private List<TripDto> joinRequestedTrips(CarCalendarInfoDto carCalendarInfoDto) {

        List<Date> requests = new ArrayList<>();
        List<TripDto> trips = new ArrayList<>();

        for (CarDayInfoDto carDayInfoDto :
                carCalendarInfoDto.getCarDayInfoList()) {
            if (carDayInfoDto.getRequested()){
                Date request = new Date();
                request = dateMapper.toDate(carDayInfoDto.getLDid());
                requests.add(request);
            }
        }
        if (requests.isEmpty()){
            return new ArrayList<>();
        }
        else if (requests.size() == 1){
            TripDto trip = new TripDto();
            trip.setStartingDate(requests.get(0));
            trip.setEndingDate(trip.getStartingDate());
            trips.add(trip);
        }
        else {
            for (int i=1; i < requests.size(); i++){
                LocalDate date = dateMapper.toLocalDate(requests.get(i));
                LocalDate predecessorDate = dateMapper.toLocalDate(requests.get(i-1));
                if (date.equals(predecessorDate)){

                }
            }
        }

        // TODO remember to ser car id

        return null;
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
