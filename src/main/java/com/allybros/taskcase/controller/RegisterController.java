package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.UserRepository;
import com.allybros.taskcase.security.UserDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String register(WebRequest request){
        // Validate user data
        try {
            String username = userDataValidator.validateUsername(request.getParameter("username"));
            String password = userDataValidator.validatePassword(request.getParameter("password"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            //TODO: Hash or encode the password
            User user = new User(username, firstName, lastName, password);
            userRepository.save(user);
            return "redirect:/login";


        } catch (InvalidAttributeValueException e) {
            // Invalid user data, redirect to the error
            return "redirect:/register?err="+e.getMessage();
        }

    }

}
