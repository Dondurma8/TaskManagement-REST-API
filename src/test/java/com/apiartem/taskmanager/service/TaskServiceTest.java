package com.apiartem.taskmanager.service;

import com.apiartem.taskmanager.dto.TaskRequest;
import com.apiartem.taskmanager.dto.TaskResponse;
import com.apiartem.taskmanager.exception.TaskNotFoundException;
import com.apiartem.taskmanager.model.Task;
import com.apiartem.taskmanager.model.TaskPriority;
import com.apiartem.taskmanager.model.TaskStatus;
import com.apiartem.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;
    private TaskRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleTask = new Task(1L, "Test task", "Description",
                TaskStatus.TODO, TaskPriority.MEDIUM, LocalDateTime.now());
        sampleRequest = new TaskRequest("Test task", "Description",
                TaskStatus.TODO, TaskPriority.MEDIUM);
    }

    @Test
    void createTask_shouldReturnCreatedTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        TaskResponse response = taskService.createTask(sampleRequest);
        assertThat(response.getTitle()).isEqualTo("Test task");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.TODO);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_whenExists_shouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        TaskResponse response = taskService.getTaskById(1L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Test task");
    }

    @Test
    void getTaskById_whenNotExists_shouldThrowTaskNotFoundException() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.getTaskById(999L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void getAllTasks_shouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));
        List<TaskResponse> tasks = taskService.getAllTasks();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test task");
    }

    @Test
    void deleteTask_whenExists_shouldDelete() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).delete(sampleTask);
    }

    @Test
    void deleteTask_whenNotExists_shouldThrowTaskNotFoundException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.deleteTask(99L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask() {
        TaskRequest updateRequest = new TaskRequest("New title", "New description",
                TaskStatus.IN_PROGRESS, TaskPriority.HIGH);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        TaskResponse response = taskService.updateTask(1L, updateRequest);
        assertThat(response.getTitle()).isEqualTo("New title");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(response.getPriority()).isEqualTo(TaskPriority.HIGH);
    }
}
