package com.allybros.taskcase.service;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void createTask(Task task){
        taskRepository.save(task);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        Iterable<Task> taskIterable = taskRepository.findAll();
        taskIterable.forEach(tasks::add);

        return tasks;
    }

    public ArrayList<Task> getTaskForUser(User attendee) {
        ArrayList<Task> tasks = new ArrayList<>();

        Iterable<Task> taskIterable = taskRepository.findTaskByAttendee(attendee);
        taskIterable.forEach(tasks::add);

        return tasks;
    }

}
