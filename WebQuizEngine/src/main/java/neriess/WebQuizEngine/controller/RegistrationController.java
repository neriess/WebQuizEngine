package neriess.WebQuizEngine.controller;


import neriess.WebQuizEngine.model.User;
import neriess.WebQuizEngine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/register")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/api/register")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        if (userService.isEmailAlreadyInUse(user.getEmail())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }
}
