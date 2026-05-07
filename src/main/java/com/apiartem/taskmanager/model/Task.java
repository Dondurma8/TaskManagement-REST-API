package com.apiartem.taskmanager.model;

import java.time.LocalDateTime;

public class Task {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;

    public Task() {
    }

    public Task(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
