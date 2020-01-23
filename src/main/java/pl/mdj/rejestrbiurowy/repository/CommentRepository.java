package pl.mdj.rejestrbiurowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mdj.rejestrbiurowy.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
