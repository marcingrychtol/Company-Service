package pl.mdj.rejestrbiurowy.repository;

import org.apache.el.lang.ELArithmetic;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
