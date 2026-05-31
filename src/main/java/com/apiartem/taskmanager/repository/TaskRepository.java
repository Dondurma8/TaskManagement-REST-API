package com.apiartem.taskmanager.repository;

import com.apiartem.taskmanager.model.Task;
import com.apiartem.taskmanager.model.TaskPriority;
import com.apiartem.taskmanager.model.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }
    public List<Task> findByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }
    public List<Task> findByPriority(TaskPriority priority) {
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }
    public List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority) {
        return tasks.stream()
                .filter(t -> t.getStatus() == status && t.getPriority() == priority)
                .collect(Collectors.toList());
    }
    public List<Task> searchByKeyword(String keyword) {
        String lower = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(lower)
                        || (t.getDescription() != null && t.getDescription().toLowerCase().contains(lower)))
                .collect(Collectors.toList());
    }
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
    public Task save(Task task) {
        tasks.add(task);
        return task;
    }
    public void delete(Task task) {
        tasks.remove(task);
    }
}
