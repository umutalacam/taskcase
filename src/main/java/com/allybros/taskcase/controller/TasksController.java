package com.allybros.taskcase.controller;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.security.TaskCaseUserDetail;
import com.allybros.taskcase.service.TaskService;
import com.allybros.taskcase.service.UserService;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.management.InvalidAttributeValueException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.allybros.taskcase.data.domain.Task.TaskState;

@Controller
public class TasksController {

    final TaskService taskService;
    final UserService userService;

    public TasksController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /**
     * Returns task list
     */
    @GetMapping("/tasks")
    public String tasks(Model model,
                        @RequestParam(value = "list", required = false) String filter,
                        @RequestParam(value = "attendee", required = false) String attendee,
                        @AuthenticationPrincipal TaskCaseUserDetail principal) {

        // Get current user
        User currentUser = principal.getUser();
        User attendant;

        // Set attendee
        if (currentUser.getRole() == User.Role.ADMIN) {
            if (attendee != null) {
                attendant = userService.getUserByUsername(attendee);
                if (attendant != null) {
                    currentUser = attendant;
                    model.addAttribute("attendee", attendee);
                } else {
                    model.addAttribute("admin_heading", true);
                }
                model.addAttribute("no_actions", true);
            } else {
                model.addAttribute("admin_heading", true);
            }
        } else {
            // Remove dashboard link from nav bar
            model.addAttribute("no_dash", true);
        }

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
    public String createTaskForm(Model model){
        model.addAttribute("old_task",
                new Task("", "", new Date(System.currentTimeMillis()), TaskState.PENDING, null));
        return "task-form";
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
            taskService.saveTask(task);
            return "redirect:/tasks";

        } catch (InvalidAttributeValueException | IllegalArgumentException e) {
            // Invalid fields
            e.printStackTrace();
            model.addAttribute("err", e.getMessage());
            return "redirect:/tasks/create";
        }
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(Model model, @PathVariable int id){
        // Retrieve old information
        // TODO: Exception handling
        Task oldTask = taskService.getTaskById(id);
        System.out.println("Edit task: "+ oldTask);
        model.addAttribute("old_task", oldTask);
        model.addAttribute("edit", true);

        return "task-form";
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(WebRequest request,
                           Model model,
                           @PathVariable int id,
                           @AuthenticationPrincipal TaskCaseUserDetail principal){

        // Get current user
        User currentUser = principal.getUser();

        // Validate task data
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

            // Retrieve old task object
            Task oldTask = taskService.getTaskById(id);
            User oldTaskAttendee = oldTask.getAttendee();

            // Check if the task is owned by principal
            if (currentUser.getRole() != User.Role.ADMIN &&
                    oldTaskAttendee.getId() != principal.getUser().getId()) {
                throw new SecurityException("Unauthorized access");
            }

            // Parse user data
            Date deadline = Date.valueOf(deadlineInput);
            TaskState state = TaskState.PENDING;
            int stateValue = Integer.parseInt(stateInput);
            if (stateValue >= 0 && stateValue <= 4) state = TaskState.values()[stateValue];

            Task newTask = new Task(titleInput, descriptionInput, deadline, state, oldTaskAttendee);
            newTask.setId(oldTask.getId());

            taskService.updateTask(newTask);
            model.addAttribute("msg", "Task is updated.");
            return "redirect:/tasks";

        } catch (InvalidAttributeValueException e) {
            model.addAttribute("edit", true);
            model.addAttribute("err", e.getMessage());
            return "task-form";
        }

    }
}
