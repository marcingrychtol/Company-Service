package pl.mdj.rejestrbiurowy.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mdj.rejestrbiurowy.entity.Client;
import pl.mdj.rejestrbiurowy.entity.Project;

import java.util.Optional;

public interface ProjectService {
    Long createProject(Client client, String name, String description);
    boolean setProjectActivity(Long id, boolean active);
    Optional<Project> getProject(Long projectId);
    Page<Project> getProjectsByPartOfName(String partOfName, Pageable pageable);
    Page<Project> getAllProjects(Pageable pageable);
    void updateProject(Long projectId, Client client, String name, String description);
    void removeProject(Long projectId);
}
