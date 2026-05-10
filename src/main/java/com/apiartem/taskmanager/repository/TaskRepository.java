package com.apiartem.taskmanager.repository;

import com.apiartem.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    public List<Task> findAll() {
        return tasks;
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
