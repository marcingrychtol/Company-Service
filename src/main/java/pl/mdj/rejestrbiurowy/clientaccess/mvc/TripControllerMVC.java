package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;


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

    @GetMapping("/delete/{id}")
    public String DeleteTrip(@PathVariable Long id){
        tripService.deleteById(id);
        return "redirect:/trips/edit";
    }


}
