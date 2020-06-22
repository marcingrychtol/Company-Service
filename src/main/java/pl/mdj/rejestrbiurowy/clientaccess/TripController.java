package pl.mdj.rejestrbiurowy.clientaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.*;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(path = "trips")
public class TripController {

    private Logger LOG = LoggerFactory.getLogger(TripController.class);

    TripService tripService;
    EmployeeService employeeService;
    CarService carService;
    DateMapper dateMapper;

    @Autowired
    public TripController(TripService tripService, EmployeeService employeeService, CarService carService, DateMapper dateMapper) {
        this.tripService = tripService;
        this.employeeService = employeeService;
        this.carService = carService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("")
    public String getAllTrips(@ModelAttribute TripDto tripDto, Model model){
        addTripsPageAttributesToModel(model, ActivePage.TRIPS, new TripDto());
        return "manager/manager-trips";
    }


    @GetMapping("/filter")
    public String getTripsFiltered(@ModelAttribute TripDto tripDto, Model model){

        addTripsPageAttributesToModel(model, ActivePage.TRIPS, tripDto);
        return "manager/manager-trips";
    }

    @PostMapping("/delete")
    public String deleteTrip(@ModelAttribute TripDto tripDto, Model model) {

        try {
            tripService.deleteByDto(tripDto);
            model.addAttribute("successMessage", "Poprawnie usunięto rezerwację!");
        } catch (WrongInputDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CannotFindEntityException e) {
            model.addAttribute("infomessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("successMessage", "Nie można usunąć, spróbuj użyć opcji Wyłącz!");
        }

        addTripsPageAttributesToModel(model, ActivePage.TRIPS, new TripDto());
        return "manager/manager-trips";
    }

    @PostMapping("/cancel")
    public String cancelTrip(@ModelAttribute TripDto tripDto, Model model){

        try {
            tripService.cancelByDto(tripDto);
            model.addAttribute("successMessage", "Poprawnie usunięto rezerwację!");
        } catch (WrongInputDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CannotFindEntityException e) {
            model.addAttribute("infomessage", e.getMessage());
        }

        addTripsPageAttributesToModel(model, ActivePage.TRIPS, new TripDto());
        return "manager/manager-trips";
    }

    @PostMapping("/edit")
    public String editTrip(@ModelAttribute TripDto tripDto, Model model){

        try {
            tripService.update(tripDto);
            model.addAttribute("successMessage", "Poprawnie zmieniono rezerwację!");
        } catch (WrongInputDataException | EntityConflictException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CannotFindEntityException e) {
            model.addAttribute("infomessage", e.getMessage());
        }

        addTripsPageAttributesToModel(model, ActivePage.TRIPS, tripDto);
        return "manager/manager-trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto, Model model){

        LocalDate requestedDate = dateMapper.toLocalDate(tripDto.getStartingDate());

        try {
            tripService.addOne(tripDto);
            model.addAttribute("successMessage","Rezerwacja dodana poprawnie!");
        } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            LOG.info(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateMapper.getDateDto(requestedDate));
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("filter", new TripDto());
        model.addAttribute("carsByDay", carService.findAllByDay(requestedDate));
        model.addAttribute("trips", tripService.findAllByDate(requestedDate));

        return "main/booking-confirmation";
    }

    @PostMapping("/add-many")
    public String postBookingForm(@ModelAttribute(name = "bookingParams") BookingParamsDto bookingParamsDto, Model model) {

        model.addAttribute("active", "booking");
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        List<TripDto> resolvedTrips = joinRequestedTrips(bookingParamsDto);
        List<TripDto> conflictedTrips = tripService.findConflictedTrips(resolvedTrips);

        if (conflictedTrips.isEmpty()) {
            model.addAttribute("infoMessage", "Brak konfliktów. Wybierz osobę i potwierdź rezerwacje.");
            return "main/booking-confirmation";
        } else {

            model.addAttribute("conflicts", conflictedTrips);
            return "main/booking-confirmation";
        }
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


    private void addTripsPageAttributesToModel(Model model, ActivePage active, TripDto tripDto){
        model.addAttribute("active", active.get());
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        if (tripDto.getFilterStartingDate() != null
                        || tripDto.getFilterEndingDate() != null
                        || tripDto.getFilterEmployeeId() != null
                        || tripDto.getFilterCarId() != null
                        || (tripDto.getFilterAdditionalMessage() != null
                        && !tripDto.getFilterAdditionalMessage().equals("") )
        ) {
            model.addAttribute("filterIsActive", "true");
            model.addAttribute("trips", tripService.findByFilter(tripDto));
            tripDto = tripService.completeFilterDtoData(tripDto);
        } else {
            model.addAttribute("filterIsActive", "false");
            model.addAttribute("trips", tripService.findAll());
        }

        model.addAttribute("tripDto", tripDto);
    }


    /**
     * Used to find all trips around request
     *
     * @param carCalendarInfoDto
     * @return
     */
    private List<TripDto> joinRequestedTrips(BookingParamsDto carCalendarInfoDto) {

        List<LocalDate> requestsDateList = new ArrayList<>();
        List<TripDto> requestedTripList = new ArrayList<>();

        for (CarDayInfoDto carDayInfoDto :
                carCalendarInfoDto.getCarDayInfoList()) {
            if (carDayInfoDto.getRequested()) {
                LocalDate requestDate;
                requestDate = carDayInfoDto.getLDid();
                requestsDateList.add(requestDate);
            }
        }

        // FIRST case - no requests
        if (requestsDateList.isEmpty()) {
            return new ArrayList<>();
        }

        // SECOND case - one request
        if (requestsDateList.size() == 1) {
            TripDto trip = new TripDto();
            trip.setStartingDate(dateMapper.toDate(requestsDateList.get(0)));
            trip.setEndingDate(trip.getStartingDate());
            trip.setCarId(carCalendarInfoDto.getCarId());
            requestedTripList.add(trip);
            return requestedTripList;
        }


        LocalDate startingDate = requestsDateList.get(0);
        LocalDate endingDate = requestsDateList.get(0);
        boolean done = false;
        for (int i = 1; i < requestsDateList.size(); i++) {

            // first we check IS GAP?
            if (!requestsDateList.get(i).minusDays(1).equals(endingDate)) {
                TripDto trip = new TripDto();
                trip.setStartingDate(dateMapper.toDate(startingDate));
                trip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(trip);
                startingDate = requestsDateList.get(i);
                endingDate = requestsDateList.get(i);

            } else if (i == requestsDateList.size() - 1) {             // was this last iteration?
                TripDto trip = new TripDto();
                trip.setStartingDate(dateMapper.toDate(startingDate));
                endingDate = requestsDateList.get(i);
                trip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(trip);
                done = true;
            } else {
                endingDate = requestsDateList.get(i);
            }

            // was this last iteration and not done yet?
            if (i == requestsDateList.size() - 1 && !done) {
                TripDto lastOneTrip = new TripDto();
                lastOneTrip.setStartingDate(dateMapper.toDate(startingDate));
                lastOneTrip.setEndingDate(dateMapper.toDate(endingDate));
                requestedTripList.add(lastOneTrip);
            }
        }

        for (TripDto trip :
                requestedTripList) {
            trip.setCarId(carCalendarInfoDto.getCarId());
        }

        return requestedTripList;
    }

}
