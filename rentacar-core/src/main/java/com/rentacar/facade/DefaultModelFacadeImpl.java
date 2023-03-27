package com.rentacar.facade;

import com.rentacar.service.CarService;
import com.rentacar.service.DayService;
import com.rentacar.service.EmployeeService;
import com.rentacar.service.TripService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class DefaultModelFacadeImpl implements ModelFacade {
    private final CarService carService;
    private final DayService dayService;
    private final EmployeeService employeeService;
    private final TripService tripService;

    public DefaultModelFacadeImpl(CarService carService, DayService dayService, EmployeeService employeeService, TripService tripService) {
        this.carService = carService;
        this.dayService = dayService;
        this.employeeService = employeeService;
        this.tripService = tripService;
    }
}
