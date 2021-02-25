package com.allybros.taskcase.service;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public Task getTaskById(int id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) return taskOptional.get();
        else throw new EntityNotFoundException("No task found with the given id.");
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        Iterable<Task> taskIterable = taskRepository.findAll();
        taskIterable.forEach(tasks::add);

        return tasks;
    }

    public ArrayList<Task> getTaskForUser(User attendee) {
        ArrayList<Task> tasks = new ArrayList<>();
        Iterable<Task> taskIterable;

        if (attendee.getRole() == User.Role.ADMIN) {
            taskIterable = taskRepository.findAll();
        } else {
            taskIterable = taskRepository.findTaskByAttendee(attendee);
        }

        taskIterable.forEach(tasks::add);
        return tasks;
    }

    public void updateTask(Task task) {
        Task oldTask = getTaskById(task.getId());

        oldTask.setTitle(task.getTitle());
        oldTask.setDescription(task.getDescription());
        oldTask.setDeadline(task.getDeadline());
        oldTask.setState(task.getState());
        oldTask.setAttendee(task.getAttendee());

        taskRepository.save(oldTask);
    }
}
