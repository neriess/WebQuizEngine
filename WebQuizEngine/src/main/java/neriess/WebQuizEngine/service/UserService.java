package neriess.WebQuizEngine.service;


import neriess.WebQuizEngine.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    boolean isEmailAlreadyInUse(String email);

    User getUserByEmail(String email);

}