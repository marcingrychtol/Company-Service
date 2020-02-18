package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "trips")
public class TripControllerMVC {

    @GetMapping("")
    public String getCallendar(Model model){
        return "trips/trips";
    }

    @GetMapping("/day/{id}")
    public String getDay(@PathVariable String id, Model model){
        return "trips/day";
    }
}
