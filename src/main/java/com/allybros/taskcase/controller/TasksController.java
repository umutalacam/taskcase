package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.security.TaskCaseUserDetail;
import com.allybros.taskcase.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.util.List;

import static com.allybros.taskcase.data.domain.Task.TaskState;

@Controller
public class TasksController {

    TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model,
                        @RequestParam(value = "list", required = false) String filter,
                        @AuthenticationPrincipal TaskCaseUserDetail principal) {

        User currentUser = principal.getUser();

        List<Task> tasks = taskService.getTaskForUser(currentUser);

        model.addAttribute("current_user", currentUser);
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
