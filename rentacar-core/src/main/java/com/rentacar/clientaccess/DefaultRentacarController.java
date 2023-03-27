package com.rentacar.clientaccess;

import com.rentacar.facade.ModelFacade;
import com.rentacar.service.CarService;
import com.rentacar.service.DayService;
import com.rentacar.service.EmployeeService;
import com.rentacar.service.TripService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class DefaultRentacarController {

    private ModelFacade modelFacade;
    private CarService carService;
    private DayService dayService;
    private EmployeeService employeeService;
    private TripService tripService;

    @Autowired
    public void setModelFacade(ModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    public void setDayService(DayService dayService) {
        this.dayService = dayService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }
}
