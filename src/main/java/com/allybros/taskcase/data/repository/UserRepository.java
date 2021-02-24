package com.allybros.taskcase.data.repository;

import com.allybros.taskcase.data.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByUsername(String username);

}
