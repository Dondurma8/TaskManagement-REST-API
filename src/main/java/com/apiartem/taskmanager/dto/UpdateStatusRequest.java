package com.apiartem.taskmanager.dto;

import com.apiartem.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {
    @NotNull(message = "Status Required")
    private TaskStatus status;

    public UpdateStatusRequest() {}

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
