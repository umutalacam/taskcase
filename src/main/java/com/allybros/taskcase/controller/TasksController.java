package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.allybros.taskcase.service.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.util.ArrayList;

import static com.allybros.taskcase.data.domain.Task.*;

@Controller
public class TasksController {

    TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model, @RequestParam(value = "list", required = false) String filter){
        System.out.println(filter);
        ArrayList<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    /**
     * Returns the task create form
     */
    @GetMapping("/tasks/create")
    public String createTaskForm(){
        return "create-task-form";
    }

    /**
     * Handles and validates the task create form data
     */
    @PostMapping("/tasks/create")
    public String createTask(WebRequest request){
        // Get request parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Date deadline = Date.valueOf(request.getParameter("deadline"));
        TaskState state = TaskState.PENDING;

        // Create new task object
        Task task = new Task(title, description, deadline, state);
        System.out.println(task);

        // Create task
        taskService.createTask(task);
        return "redirect:/tasks";
    }
}
