package pl.mdj.rejestrbiurowy.clientaccess.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.service.interfaces.TaskService;

@AllArgsConstructor
@RestController
@RequestMapping("task")
public class TaskController {
    /*
    * Task
    * TaskCategory
     */
    TaskService taskService;

//    @PostMapping(path="/instance", consumes = "application/json")
//    public Task addTask(@RequestBody Task task){
//        taskService.(task);
//        return task;
//    }


}
