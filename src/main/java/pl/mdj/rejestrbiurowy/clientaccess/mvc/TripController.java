package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "mvc")
public class TripController {

    @RequestMapping(path = "/tasks")
    public ModelAndView taskView(){
        return new ModelAndView();
    }
}
