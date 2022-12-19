package neriess.WebQuizEngine.service;

import neriess.WebQuizEngine.model.CompleteQuiz;
import neriess.WebQuizEngine.model.Quiz;
import neriess.WebQuizEngine.model.ResponseToAnswer;
import neriess.WebQuizEngine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface QuizService {

    Quiz saveQuiz(Quiz quiz);

    Page<Quiz> getAllQuizzes(int numberOfPages);

    Quiz getQuizById(Long id);

    ResponseToAnswer checkAnswer(Long id, List<Integer> answer, User user);

    ResponseEntity<Void> deleteQuizById(Long id, Principal principal);

    Page<CompleteQuiz>getCompleteQuizzesByUser(int numberOfPages, User currentUser);
}
