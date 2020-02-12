package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "mvc")
public class TripMvcController {

    @GetMapping(path = "/index")
    public String taskView(){
        return ("index");
    }
}
