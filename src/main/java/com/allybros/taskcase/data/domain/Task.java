package com.allybros.taskcase.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Data
public class Task {
    public enum TaskState {
        PENDING, COMPLETED, POSTPONED, CANCELLED}

    @Id
    private int id;
    private String title;
    private Date deadline;
    private TaskState state;

    @ManyToOne
    private User attendee;

    public Task() {

    }

    public Task(String title, Date deadline, TaskState state) {
        this.title = title;
        this.deadline = deadline;
        this.state = state;
    }
}
