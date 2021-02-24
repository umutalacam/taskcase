package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.UserRepository;
import com.allybros.taskcase.security.UserDataValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.management.InvalidAttributeValueException;

@Controller
public class RegisterController {

    final UserDataValidator userDataValidator;
    final UserRepository userRepository;

    public RegisterController(UserDataValidator userDataValidator, UserRepository userRepository) {
        this.userDataValidator = userDataValidator;
        this.userRepository = userRepository;
    }

    /**
     * Returns the register form as a response
     */
    @GetMapping("/register")
    public String registerForm(){
        return "register-form";
    }

    @PostMapping("/register")
    public String register(Model model, WebRequest request){
        // Validate user data
        try {
            String username = userDataValidator.validateUsername(request.getParameter("username"));
            String password = userDataValidator.validatePassword(request.getParameter("password"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            // Encode password
            String encodedPassword = new BCryptPasswordEncoder().encode(password);

            User user = new User(username, firstName, lastName, encodedPassword);
            userRepository.save(user);

            return "redirect:/login";


        } catch (InvalidAttributeValueException e) {
            // Invalid user data, redirect to the error
            model.addAttribute("err", e.getMessage());
            return "register-form";
        }

    }

}
