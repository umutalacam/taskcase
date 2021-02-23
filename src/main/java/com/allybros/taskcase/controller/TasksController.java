package com.allybros.taskcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TasksController {

    @GetMapping("/tasks")
    public String tasks(){
        return "tasks";
    }
}
