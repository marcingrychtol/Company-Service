package pl.mdj.rejestrbiurowy.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import pl.mdj.rejestrbiurowy.entity.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}
