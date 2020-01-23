package pl.mdj.rejestrbiurowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.entity.Task;
import pl.mdj.rejestrbiurowy.repository.TaskRepository;

@RestController
@RequestMapping("/task")
public class TaskController {
    /*
    * Task
    * TaskCategory
     */

    @Autowired
    TaskRepository taskRepository;

    @PostMapping(path="/instance", consumes = "application/json")
    public Task addTask(@RequestBody Task task){
        taskRepository.save(task);
        return task;
    }


}
