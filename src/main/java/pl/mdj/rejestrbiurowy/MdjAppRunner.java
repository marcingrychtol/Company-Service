package pl.mdj.rejestrbiurowy;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.service.interfaces.*;


@Component
@AllArgsConstructor
public class MdjAppRunner {

    private final static Logger LOG = LoggerFactory.getLogger(MdjAppRunner.class);

    private CarService carService;
    private ClientService clientService;
    private CommentService commentService;
    private EmployeeService employeeService;
    private LocationService locationService;
    private ProjectService projectService;
    private TaskService taskService;
    private TripService tripService;


}
