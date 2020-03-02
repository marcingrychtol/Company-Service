package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;

import java.util.Date;


@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    TripService tripService;

    @Autowired
    public TripControllerMVC(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("")
    public String getCalendar(Model model){
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto, Model model){
        model.addAttribute("tripVirtual", tripService.addOne(tripDto));
        return "redirect:/trips/";
    }

    @GetMapping("/edit")
    public String editTrips(Model model){
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips-edit";
    }

    @InitBinder
    public void allowEmptyDateBinding( WebDataBinder binder )
    {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( Date.class, new StringTrimmerEditor( true ));
    }

    @GetMapping("/delete/{id}")
    public String DeleteTrip(@PathVariable Long id){
        tripService.deleteById(id);
        return "redirect:/trips/edit";
    }

    // TODO @InitBinder


}
