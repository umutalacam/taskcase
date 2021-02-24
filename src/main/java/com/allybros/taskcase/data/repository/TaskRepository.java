package com.allybros.taskcase.data.repository;

import com.allybros.taskcase.data.domain.Task;
import com.allybros.taskcase.data.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findTaskByAttendee(User user);
}