package com.example.taskproject.services;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.services.DTO.CommentDto;
import com.example.taskproject.services.DTO.TaskDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    void createTask(TaskDto taskDto);
    void updateTask(Long taskId, TaskDto taskDto);
    void deleteTask(Long taskId);
    void updateTaskStatus(Long taskId, TaskStatus status);
    void updateTaskPriority(Long taskId, TaskPriority priority);
    void assignTaskToUser(Long taskId, Long userId);
    TaskDto getTaskById(Long id);

    Page<TaskDto> getAll(int page, int size);
}
