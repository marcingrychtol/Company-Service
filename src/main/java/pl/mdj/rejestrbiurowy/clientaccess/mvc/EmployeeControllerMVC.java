package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mdj.rejestrbiurowy.entity.Employee;
import pl.mdj.rejestrbiurowy.service.interfaces.EmployeeService;

@Controller
@RequestMapping("employees")
public class EmployeeControllerMVC {

    EmployeeService employeeService;

    @Autowired
    public EmployeeControllerMVC(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public String getEmployees(Model model){
        model.addAttribute("employees", employeeService.getAll());
        return "employees/employees";
    }

    @GetMapping("/edit")
    public String editEmployees(Model model){
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("newEmployee", new Employee());
        return "employees/employees-edit";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee){
        employeeService.addOne(employee);
        return "redirect:/employees/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id){
        employeeService.deleteById(Long.parseLong(id));
        return "redirect:/employees/edit";
    }
}
