package com.allybros.taskcase.data.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Task {
    public enum TaskState {
        PENDING, COMPLETED, POSTPONED, CANCELLED}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private TaskState state;

    @ManyToOne
    private User attendee;

    public Task() {

    }

    public Task(String title, String description, Date deadline, TaskState state) {
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", state=" + state +
                '}';
    }
}
