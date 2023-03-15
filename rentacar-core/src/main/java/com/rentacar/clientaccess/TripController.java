package com.rentacar.clientaccess;

import com.rentacar.exceptions.CannotFindEntityException;
import com.rentacar.exceptions.EntityConflictException;
import com.rentacar.exceptions.EntityNotCompleteException;
import com.rentacar.exceptions.WrongInputDataException;
import com.rentacar.model.DateFactory;
import com.rentacar.model.dto.BookingParamsDto;
import com.rentacar.model.dto.TripDto;
import com.rentacar.service.CarService;
import com.rentacar.service.EmployeeService;
import com.rentacar.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;


@Controller
@RequestMapping(path = "trips")
public class TripController {

    private Logger LOG = LoggerFactory.getLogger(TripController.class);

    TripService tripService;
    EmployeeService employeeService;
    CarService carService;
    DateFactory dateFactory;

    @Autowired
    public TripController(TripService tripService, EmployeeService employeeService, CarService carService, DateFactory dateFactory) {
        this.tripService = tripService;
        this.employeeService = employeeService;
        this.carService = carService;
        this.dateFactory = dateFactory;
    }

    @GetMapping("")
    public String getAllActiveTrips(@ModelAttribute TripDto tripDto, Model model) {
        addTripsPageAttributesToModel(model, ActivePage.TRIPS, new TripDto());
        return "manager/manager-trips";
    }

    @GetMapping("/cancelled")
    public String getAllCancelledTrips(@ModelAttribute TripDto tripDto, Model model) {

        model.addAttribute("active", ActivePage.TRIPS);
        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("filterIsActive", "false");
        model.addAttribute("trips", tripService.findAll().stream().filter(TripDto::getCancelled).collect(Collectors.toList()));
        model.addAttribute("tripDto", tripDto);

        return "manager/manager-trips";
    }


    @GetMapping("/filter")
    public String getTripsFiltered(@ModelAttribute TripDto tripDto, Model model) {

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
    public String cancelTrip(@ModelAttribute TripDto tripDto, Model model) {

        try {
            tripService.cancelByDto(tripDto);
            model.addAttribute("successMessage", "Poprawnie anulowano rezerwację!");
        } catch (WrongInputDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (CannotFindEntityException e) {
            model.addAttribute("infomessage", e.getMessage());
        }

        addTripsPageAttributesToModel(model, ActivePage.TRIPS, new TripDto());
        return "manager/manager-trips";
    }

    @PostMapping("/edit")
    public String editTrip(@ModelAttribute TripDto tripDto, Model model) {

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
    public String addTrip(@ModelAttribute TripDto tripDto, Model model) {

        LocalDate requestedDate = tripDto.getStartingDate();

        if (tripDto.getEndingDate() != null && DAYS.between(tripDto.getStartingDate(), tripDto.getEndingDate()) > 56) {
            model.addAttribute("errorMessage", "Nie rób se jaj...");
        } else {

            try {
                tripService.addOne(tripDto);
                model.addAttribute("successMessage", "Rezerwacja dodana poprawnie!");
            } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
                LOG.info(e.getMessage());
                model.addAttribute("errorMessage", e.getMessage());
            }

        }

        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("requestedDate", dateFactory.getDateDto(requestedDate));
        model.addAttribute("newTrip", new TripDto());
        model.addAttribute("filter", new TripDto());
        model.addAttribute("carsByDay", carService.findAllByDay(requestedDate));
        model.addAttribute("trips", tripService.findAllByDate(requestedDate));

        return "main/booking-confirmation";
    }

    @PostMapping("/add-many")
    public String postBookingForm(@ModelAttribute(name = "requestParams") BookingParamsDto bookingParamsDto, Model
            model) {

        model.addAttribute("active", "booking");
        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        List<TripDto> conflictedTrips = tripService.findConflictedTrips(tripService.joinRequestedTrips(bookingParamsDto));
        if (!conflictedTrips.isEmpty()) {
            model.addAttribute("conflicts", conflictedTrips);
        }

        List<TripDto> reservations = null;
        try {
            reservations = tripService.addAll(bookingParamsDto);
            model.addAttribute("infoMessage", "Rezerwacja powiodła się");
            model.addAttribute("reservations", reservations);
        } catch (EntityNotCompleteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("reservations", new ArrayList<>());
        }

        return "main/booking-confirmation";

    }


    private void addTripsPageAttributesToModel(Model model, ActivePage active, TripDto tripDto) {

        model.addAttribute("active", active.get());
        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("employees", employeeService.findAll());

        if (tripDto.getFilterStartingDate() != null
                || tripDto.getFilterEndingDate() != null
                || tripDto.getFilterEmployeeId() != null
                || tripDto.getFilterCarId() != null
                || (tripDto.getFilterAdditionalMessage() != null
                && !tripDto.getFilterAdditionalMessage().equals(""))
        ) {
            model.addAttribute("filterIsActive", "true");
            model.addAttribute("trips", tripService.findByFilter(tripDto));
            tripDto = tripService.completeFilterDtoData(tripDto);
        } else {
            model.addAttribute("filterIsActive", "false");
            model.addAttribute("trips", tripService.findAllActive().stream()
                    .filter(t -> t.getEndingDate().isAfter(LocalDate.now().minusDays(1)))
                    .collect(Collectors.toList()));
        }

        model.addAttribute("tripDto", tripDto);
    }

}
