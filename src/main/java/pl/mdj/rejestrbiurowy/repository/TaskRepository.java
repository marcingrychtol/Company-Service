package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mdj.rejestrbiurowy.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {



}
