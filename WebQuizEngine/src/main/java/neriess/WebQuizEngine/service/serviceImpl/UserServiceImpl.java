package neriess.WebQuizEngine.service.serviceImpl;

import neriess.WebQuizEngine.model.User;
import neriess.WebQuizEngine.repository.UserRepository;
import neriess.WebQuizEngine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    @Override
    public User saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isEmailAlreadyInUse(String email) {
        List<User> listOfUsers = userRepository.findAll();
        for (User user : listOfUsers) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> listOfUsers = userRepository.findAll();
        for (User user : listOfUsers) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }
}
