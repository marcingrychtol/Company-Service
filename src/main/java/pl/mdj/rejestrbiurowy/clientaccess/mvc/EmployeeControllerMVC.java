package pl.mdj.rejestrbiurowy.clientaccess.mvc;

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
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.service.EmployeeService;

import java.time.LocalDate;

@Controller
@RequestMapping("employees")
public class EmployeeControllerMVC {

    EmployeeService employeeService;
    DateMapper dateMapper;

    @Autowired
    public EmployeeControllerMVC(EmployeeService employeeService, DateMapper dateMapper) {
        this.employeeService = employeeService;
        this.dateMapper = dateMapper;
    }

    @GetMapping("")
    public String getEmployees(Model model){
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("employees", employeeService.getAllActive());
        return "main/employees";
    }

    @GetMapping("/manager")
    public String editEmployees(Model model){
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("newEmployee", new EmployeeDto());
        return "manager/manager-employees";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDto employee, Model model){
        try {
            employeeService.addOne(employee);
            model.addAttribute("successMessage", "Poprawnie dodano pracownika!");
        } catch (EntityNotCompleteException | EntityConflictException | WrongInputDataException | CannotFindEntityException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("newEmployee", new EmployeeDto());
        return "manager/manager-employees";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.deleteByDto(employeeDto);
            model.addAttribute("successMessage", "Poprawnie usunięto pracownika!");
        } catch (WrongInputDataException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e){
            employeeService.cancelByDto(employeeDto);
            model.addAttribute("infoMessage", "Nie można usunąć pracownika, ustawiono jako nieaktywny!");
        }
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("newEmployee", new EmployeeDto());
        return "manager/manager-employees";
    }

    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute EmployeeDto employeeDto, Model model){
        try {
            employeeService.update(employeeDto);
            model.addAttribute("successMessage", "Poprawnie zmieniono dane pracownika!");
        } catch (WrongInputDataException | EntityConflictException | CannotFindEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("today", dateMapper.getDateDto(LocalDate.now()));

        model.addAttribute("employees", employeeService.getAllActive());
        model.addAttribute("newEmployee", new EmployeeDto());
        return "manager/manager-employees";
    }
}
