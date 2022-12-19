package neriess.WebQuizEngine.controller;


import neriess.WebQuizEngine.model.*;
import neriess.WebQuizEngine.service.QuizService;
import neriess.WebQuizEngine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;


    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") int page) {
        return quizService.getAllQuizzes(page);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<ResponseToAnswer> checkSolution(@PathVariable Long id,
                                                          @RequestBody Answer answer,
                                                          Principal principal) {
        User currentUser = userService.getUserByEmail(principal.getName());
        ResponseToAnswer response = quizService.checkAnswer(id, answer.getAnswer(), currentUser);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> saveQuiz(@Valid @RequestBody Quiz quiz, Principal principal) {
        User currentUser = userService.getUserByEmail(principal.getName());
        quiz.setUser(currentUser);
        return new ResponseEntity<>(quizService.saveQuiz(quiz), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/quizzes/{id}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable Long id, Principal principal) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return quizService.deleteQuizById(id, principal);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompleteQuiz> getCompleteQuizzesByUser(@RequestParam(required = false, defaultValue = "0") int page,
                                                       Principal principal) {
        User currentUser = userService.getUserByEmail(principal.getName());
        return quizService.getCompleteQuizzesByUser(page,currentUser);
    }

}
