package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.mdj.rejestrbiurowy.entity.Task;
import pl.mdj.rejestrbiurowy.entity.enums.ETaskStatus;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    Page<Task> findByStatus(ETaskStatus status, Pageable pageable);
    Page<Task> findByDescriptionContaining(String partOfDescription, Pageable pageable);

}
