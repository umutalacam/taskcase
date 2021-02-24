package com.allybros.taskcase.service;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void createTask(Task task){
        taskRepository.save(task);
    }


}
