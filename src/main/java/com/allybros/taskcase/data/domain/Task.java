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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date deadline;

    @Column(nullable = false)
    private TaskState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee")
    private User attendee;

    public Task() {

    }

    public Task(String title, String description, Date deadline, TaskState state, User attendee) {
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.state = state;
        this.attendee = attendee;
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
