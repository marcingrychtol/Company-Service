package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.clientaccess.dto.TripDto;
import pl.mdj.rejestrbiurowy.service.interfaces.TripService;
import pl.mdj.rejestrbiurowy.service.mappers.TripMapper;


@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    TripService tripService;
    TripMapper tripMapper;

    @Autowired
    public TripControllerMVC(TripService tripService, TripMapper tripMapper) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
    }

    @GetMapping("")
    public String getCalendar(Model model){
        model.addAttribute("trips", tripService.getAll());
        return "trips/trips";
    }

    @PostMapping("/add")
    public String addTrip(@ModelAttribute TripDto tripDto){
        tripService.addOne(tripMapper.mapToEntity(tripDto));
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
