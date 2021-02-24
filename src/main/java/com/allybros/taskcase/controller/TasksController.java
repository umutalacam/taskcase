package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.allybros.taskcase.service.*;

import java.sql.Date;

@Controller
public class TasksController {

    TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(){
        Task task = new Task("Example task", new Date(0), Task.TaskState.PENDING);
        taskService.createTask(task);
        return "tasks";
    }
}
