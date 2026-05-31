package com.apiartem.taskmanager.dto;

import com.apiartem.taskmanager.model.TaskPriority;
import com.apiartem.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                  // генерирует геттеры, сеттеры, equals, hashCode, toString
@NoArgsConstructor     // генерирует конструктор без аргументов
@AllArgsConstructor    // генерирует конструктор со всеми аргументами
public class TaskRequest {
    @NotBlank(message = "Title cannot be empty")

    @Size(min = 2, max = 100, message = "The title must be between 2 and 100 characters long")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Status required")
    private TaskStatus status;

    @NotNull(message = "Priority required")
    private TaskPriority priority;

}