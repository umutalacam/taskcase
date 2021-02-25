package com.allybros.taskcase.service;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        Iterable<User> userIterable = this.userRepository.findAll();

        userIterable.forEach(users::add);
        return users;
    }

    @PostConstruct
    private void initializeDemoAccounts(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User test1 = new User("test1", "Test1", "Test", encoder.encode("1234"));
        User test2 = new User("test2", "Test2", "Test", encoder.encode("1234"));
        User admin = new User("admin", "Admin", "Administrator", encoder.encode("1234"));
        admin.setRole(User.Role.ADMIN);

        this.createUser(test1);
        this.createUser(test2);
        this.createUser(admin);
    }
}
