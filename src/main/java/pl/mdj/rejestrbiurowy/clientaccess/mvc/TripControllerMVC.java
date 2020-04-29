package pl.mdj.rejestrbiurowy.clientaccess.mvc;

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
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    private Logger LOG = LoggerFactory.getLogger(TripControllerMVC.class);

    TripService tripService;
    EmployeeService employeeService;
    CarService carService;
    DateMapper dateMapper;

    @Autowired
    public TripControllerMVC(TripService tripService, EmployeeService employeeService, CarService carService, DateMapper dateMapper) {
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
        model.addAttribute("cars", carService.getAvailableCarsByDay(requestedDate));
        model.addAttribute("trips", tripService.findAllByDate(requestedDate));

        return "main/browser";
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

}
