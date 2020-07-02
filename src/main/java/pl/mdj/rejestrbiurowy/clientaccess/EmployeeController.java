package pl.mdj.rejestrbiurowy.clientaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.exceptions.WrongInputDataException;
import pl.mdj.rejestrbiurowy.model.dto.EmployeeDto;
import pl.mdj.rejestrbiurowy.model.DateFactory;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.time.LocalDate;

@Controller
@RequestMapping("employees")
public class EmployeeController {

    EmployeeService employeeService;
    DateFactory dateFactory;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DateFactory dateFactory) {
        this.employeeService = employeeService;
        this.dateFactory = dateFactory;
    }

    @GetMapping("/manager")
    public String getEmployees(Model model){
        model.addAttribute("active", "data");
        model.addAttribute("today", dateFactory.getDateDto(LocalDate.now()));
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("newEmployee", new EmployeeDto());
        return "manager/manager-employees";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDto employee, Model model){
        try {
            employeeService.addOne(employee);
            model.addAttribute("successMessage", "Poprawnie dodano pracownika!");
        } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getEmployees(model);
    }

    @PostMapping("/delete")
    public String deleteEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.deleteByDto(employeeDto);
            model.addAttribute("successMessage", "Poprawnie usunięto pracownika!");
        } catch (WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e){
            model.addAttribute("infoMessage", "Nie można usunąć pracownika, gdyż ma rezerwacje. Spróbuj opcji Wyłącz!");
        }

        return getEmployees(model);
    }

    @PostMapping("/cancel")
    public String cancelEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.cancelByDto(employeeDto);
            model.addAttribute("successMessage", "Poprawnie wyłączono pracownika!");
        } catch (CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e){
            model.addAttribute("infoMessage", "DataIntegrityViolationException - WTF?");
        } catch (WrongInputDataException ignored) {
        }

        return getEmployees(model);
    }

    @PostMapping("/enable")
    public String enableEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.enableByDto(employeeDto);
            model.addAttribute("successMessage", "Poprawnie włączono pracownika!");
        } catch (CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getEmployees(model);
    }

    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.update(employeeDto);
            model.addAttribute("successMessage", "Poprawnie zmieniono dane pracownika!");
        } catch (WrongInputDataException | EntityConflictException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return getEmployees(model);
    }
}
