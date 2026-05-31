package com.apiartem.taskmanager.service;

import com.apiartem.taskmanager.dto.TaskRequest;
import com.apiartem.taskmanager.dto.TaskResponse;
import com.apiartem.taskmanager.exception.TaskNotFoundException;
import com.apiartem.taskmanager.model.Task;
import com.apiartem.taskmanager.model.TaskPriority;
import com.apiartem.taskmanager.model.TaskStatus;
import com.apiartem.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private Long nextId = 1L;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest request) {
        log.info("Task creation: title='{}', priority={}", request.getTitle(), request.getPriority());
        Task task = new Task();
        task.setId(nextId++);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        log.info("Task created with id={}", savedTask.getId());
        return mapToResponse(savedTask);
    }
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public List<TaskResponse> getTasksByFilter(TaskStatus status, TaskPriority priority) {
        List<Task> result;
        if (status != null && priority != null) {
            result = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            result = taskRepository.findByStatus(status);
        } else if (priority != null) {
            result = taskRepository.findByPriority(priority);
        } else {
            result = taskRepository.findAll();
        }
        return result.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public List<TaskResponse> searchTasks(String keyword) {
        return taskRepository.searchByKeyword(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public TaskResponse getTaskById(Long id) {
        log.debug("Task search with id={}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task with id={} not found", id);
                    return new TaskNotFoundException("Task with id " + id + " not found");
                });
        return mapToResponse(task);
    }
    public void deleteTask(Long id) {
        log.info("Task removal with id={}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        taskRepository.delete(task);
        log.info("Task with id={} removed", id);
    }
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setUpdatedAt(LocalDateTime.now()); // ДОБАВИТЬ
        return mapToResponse(task);
    }
    public TaskResponse updateStatus(Long id, TaskStatus newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        task.setStatus(newStatus);
        task.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(task);
    }
    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
                task.getStatus(), task.getPriority(), task.getCreatedAt(), task.getUpdatedAt());
    }
}