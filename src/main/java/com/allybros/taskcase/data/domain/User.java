package com.allybros.taskcase.data.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    public enum Role {STD_USER, ADMIN}

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String encodedPassword;

    @OneToMany(targetEntity = Task.class, mappedBy = "attendee")
    private List<Task> tasks;

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
