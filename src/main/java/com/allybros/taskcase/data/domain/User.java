package com.allybros.taskcase.data.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    public enum Role {STD_USER, ADMIN}

    @Id
    private int id;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String encodedPassword;


    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}
