package com.allybros.taskcase.security;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.UserRepository;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.management.InvalidAttributeValueException;

@Component
@Scope("singleton")
public class UserDataValidator {

    @Autowired
    private UserRepository userRepository;


    public String validateUsername(@NotNull String username) throws InvalidAttributeValueException {
        if (username.isEmpty())
            throw new InvalidAttributeValueException("Username can't be empty.");

        if (username.length() < 5)
            throw new InvalidAttributeValueException("Username must be longer than 5 characters.");

        if (!username.matches("[A-z0-9]+"))
            throw new InvalidAttributeValueException("Username can't include special characters.");

        // Check if username is already taken
        User user = userRepository.findUserByUsername(username);
        if (user != null)
            throw new InvalidAttributeValueException("Username already exists.");

        return username;
    }

    public String validatePassword(@NotNull String password) throws InvalidAttributeValueException {
        if (password.isEmpty())
            throw new InvalidAttributeValueException("Password can't be empty.");

        if (password.length() < 6)
            throw new InvalidAttributeValueException("Password must be longer than 6 characters.");

        if (!password.matches("[A-z0-9]+"))
            throw new InvalidAttributeValueException("Password can't include special characters.");

        return password;
    }

}
