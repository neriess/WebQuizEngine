package neriess.WebQuizEngine.service.serviceImpl;

import neriess.WebQuizEngine.model.CompleteQuiz;
import neriess.WebQuizEngine.model.Quiz;
import neriess.WebQuizEngine.model.ResponseToAnswer;
import neriess.WebQuizEngine.model.User;
import neriess.WebQuizEngine.repository.CompleteQuizRepository;
import neriess.WebQuizEngine.repository.QuizRepository;
import neriess.WebQuizEngine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.*;

@Component
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CompleteQuizRepository completeQuizRepository;


    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Page<Quiz> getAllQuizzes(int page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public Quiz getQuizById(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isPresent()) {
            return quiz.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with id: " + id + " not found.");
        }
    }

    @Override
    public ResponseToAnswer checkAnswer(Long id, List<Integer> answer, User currentUser) {
        ResponseToAnswer response;
        Quiz quiz = getQuizById(id);

        if (quiz == null) return null;

        int[] answersFromRepository = quiz.getAnswer();
        List<Integer> listOfAnswers = new ArrayList<>(Arrays.stream(answersFromRepository)
                .boxed()
                .toList());

        Collections.sort(listOfAnswers);
        Collections.sort(answer);

        if (Objects.equals(answer, listOfAnswers)) {
            completeQuizRepository.save(new CompleteQuiz(quiz.getId(), currentUser));
            response = new ResponseToAnswer(true, "Congratulations, you're right!");
        } else {
            response = new ResponseToAnswer(false, "Wrong answer! Please, try again.");
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> deleteQuizById(Long id, Principal principal) {
        String currentUserName = principal.getName();
        Quiz quiz = getQuizById(id);

        if (quiz.getUser().getEmail().equals(currentUserName)) {
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public Page<CompleteQuiz> getCompleteQuizzesByUser(int page, User currentUser) {
        return completeQuizRepository.findByUserEmail(PageRequest.of(page, 10,
                Sort.by("completedAt").descending()), currentUser.getEmail());
    }
}
