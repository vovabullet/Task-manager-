package com.example.taskproject.services;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.services.DTO.TaskDto;

public interface TaskService {
    void createTask(TaskDto taskDto);
    void updateTask(Long taskId, TaskDto taskDto);
    void deleteTask(Long taskId);
    void updateTaskStatus(Long taskId, TaskStatus status);
    void updateTaskPriority(Long taskId, TaskPriority priority);
    void assignTaskToUser(Long taskId, Long userId);
    TaskDto getTaskById(Long id);
}
