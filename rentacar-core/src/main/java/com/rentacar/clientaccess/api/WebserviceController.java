package com.rentacar.clientaccess.api;

import com.rentacar.clientaccess.DefaultRentacarController;
import com.rentacar.model.dto.CarDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebserviceController extends DefaultRentacarController {

    @GetMapping("cars")
    private List<CarDto> getCars(){
        return getCarService().findAll();
    }
}
