package pl.mdj.rejestrbiurowy.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.entity.Task;
import pl.mdj.rejestrbiurowy.repository.ProjectRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.ProjectService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("project")
public class ProjectController {
    /*
    * Project
     */

    @GetMapping(value = "/{projectId}/tasks", produces = "application/json")
            public ResponseEntity<List<Task>> getProjectTasks(@PathVariable("projectId") long id){
        return null;
    }

    ProjectService projectService;
}
