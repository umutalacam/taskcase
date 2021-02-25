package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.security.TaskCaseUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model,
                    @AuthenticationPrincipal TaskCaseUserDetail principal){

        if (principal != null) {
            // User logged in
            if (principal.getUser().getRole() == User.Role.ADMIN) {
                // ADMIN USER
                return "redirect:/dashboard";
            } else {
                // STD USER
                return "redirect:/tasks";
            }
        }
        model.addAttribute("no_tasks",true);
        model.addAttribute("no_dash",true);
        return "home";
    }

}
