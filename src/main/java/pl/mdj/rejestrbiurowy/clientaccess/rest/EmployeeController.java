package pl.mdj.rejestrbiurowy.clientaccess.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

@AllArgsConstructor
@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    /*
    * Employee
     */

//    @PostMapping(value = "/create", produces = "application/json")
//    public ResponseEntity<CreateEmployeeResponse> createEmployee(@RequestBody CreateEmployeeRequest request){
//        return null;
//    }
//
//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<GetEmployeeByIdResponse> getEmployeeById(@PathVariable("id") long id){
//        return employeeService.fin
//
//    }

    EmployeeService employeeService;
}
