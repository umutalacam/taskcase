package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class DashboardController {

    UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Load all users
        ArrayList<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "dashboard";
    }
}
