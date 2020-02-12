package pl.mdj.rejestrbiurowy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mdj.rejestrbiurowy.entity.Client;
import pl.mdj.rejestrbiurowy.entity.Project;
import pl.mdj.rejestrbiurowy.repository.ClientRepository;
import pl.mdj.rejestrbiurowy.repository.ProjectRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.ProjectService;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    ClientRepository clientRepository;


    @Override
    public Long createProject(Client client, String name, String description) {
        Project project = new Project();
        project.setClient(client);
        project.setName(name);
        project.setDescription(description);
        projectRepository.save(project);
        return project.getId();
    }

    @Override
    public boolean setProjectActivity(Long id, boolean active) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()){
            project.get().setActive(active);
        }
        return active;
    }

    @Override
    public Optional<Project> getProject(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Page<Project> getProjectsByPartOfName(String partOfName, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return null;
    }

    @Override
    public void updateProject(Long projectId, Client client, String name, String description) {

    }

    @Override
    public void removeProject(Long projectId) {

    }
}
