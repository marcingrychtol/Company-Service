package pl.mdj.rejestrbiurowy.clientaccess.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.interfaces.BasicController;
import pl.mdj.rejestrbiurowy.model.dto.DateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("calendar")
public class CalendarControllerMVC {

    BasicController controllerMVC;

    @Autowired
    public CalendarControllerMVC(BasicController controllerMVC) {
        this.controllerMVC = controllerMVC;
    }

    @GetMapping("")
    public String getCallendar(Model model) {

        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            dateList.add(LocalDate.now().plusDays((long)i));
        }

        model.addAttribute("dateList", dateList);
        model.addAttribute("filterDateWrap", new DateDto());
        return "calendar/calendar";
    }

    @GetMapping("/pick")
    public String setDay(Model model){
        return controllerMVC.getHome(model);
    }

    @GetMapping("/day/{id}")
    public String getDay(@PathVariable String id, Model model) {
        return "calendar/day";
    }
}
