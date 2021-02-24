package com.allybros.taskcase.data.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    public enum Role {STD_USER, ADMIN}

    @Id
    @GeneratedValue
    private int id;

    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String encodedPassword;

    public User() {
    }

    public User(String username, String firstName, String lastName, String encodedPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.encodedPassword = encodedPassword;
        this.role = Role.STD_USER;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}
