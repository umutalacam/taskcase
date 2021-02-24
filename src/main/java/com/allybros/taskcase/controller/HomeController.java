package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }

}
