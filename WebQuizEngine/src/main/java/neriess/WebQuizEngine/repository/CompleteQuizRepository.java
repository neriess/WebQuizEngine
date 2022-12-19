package neriess.WebQuizEngine.repository;

import neriess.WebQuizEngine.model.CompleteQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteQuizRepository extends JpaRepository<CompleteQuiz, Long> {
    Page<CompleteQuiz> findByUserEmail(Pageable request, String userName);
}
