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

import javax.management.InvalidAttributeValueException;
import java.io.Console;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        // Get current user
        User currentUser = principal.getUser();

        // Get filter
        final TaskState filteredState;
        if (filter != null) {
            switch (filter) {
                case "completed":
                    filteredState = TaskState.COMPLETED;
                    break;
                case "postponed":
                    filteredState = TaskState.POSTPONED;
                    break;
                case "cancelled":
                    filteredState = TaskState.CANCELLED;
                    break;
                default:
                    filteredState = TaskState.PENDING;
                    filter = "pending";
            }
        } else {
            filter = "pending";
            filteredState = TaskState.PENDING;
        }

        // Filter tasks TODO: Optimal way?
        List<Task> tasks = taskService.getTaskForUser(currentUser).stream()
                .filter((Task task) -> task.getState() == filteredState)
                .collect(Collectors.toList());

        model.addAttribute("current_user", currentUser);
        model.addAttribute("tasks", tasks);
        model.addAttribute("filter", filter);
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
    public String createTask(Model model,
                             WebRequest request,
                             @AuthenticationPrincipal TaskCaseUserDetail principal){
        // Get request parameters
        String titleInput = request.getParameter("title");
        String descriptionInput = request.getParameter("description");
        String deadlineInput = request.getParameter("deadline");
        String stateInput = request.getParameter("state");

        try {
            // Check if fields are null
            if (titleInput == null ||
                descriptionInput == null ||
                deadlineInput == null ||
                stateInput == null )
                throw new InvalidAttributeValueException("Fields are missing.");

            // Parse deadline and state data
            Date deadline = Date.valueOf(deadlineInput);

            TaskState state = TaskState.PENDING;
            int stateValue = Integer.parseInt(stateInput);
            if (stateValue>=0 && stateValue<=4) state = TaskState.values()[stateValue];

            // Create new task object
            User attendee = principal.getUser();
            Task task = new Task(titleInput, descriptionInput, deadline, state, attendee);
            System.out.println(task);

            // Create task
            taskService.createTask(task);
            return "redirect:/tasks";

        } catch (InvalidAttributeValueException | IllegalArgumentException e) {
            // Invalid fields
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "redirect:/tasks/create";
        }
    }
}
