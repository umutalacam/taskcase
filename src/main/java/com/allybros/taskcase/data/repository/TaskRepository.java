package com.allybros.taskcase.data.repository;

import com.allybros.taskcase.data.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {}