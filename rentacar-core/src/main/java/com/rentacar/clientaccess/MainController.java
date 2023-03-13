package com.rentacar.clientaccess;

import com.rentacar.aspect.RentacarLoggable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    @RentacarLoggable
    public String getHome(){
        return "redirect:/calendar/day";
    }

}
