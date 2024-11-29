package com.example.taskproject.services;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.models.Task;
import com.example.taskproject.services.DTO.TaskDto;

import java.util.List;

public interface TaskService {
    void createTask(TaskDto taskDto);
    void updateTask(Long taskId, TaskDto taskDto);
    void deleteTask(Long taskId);
    /* TODO тут используется пагинация, надо разобраться что это такое
    List<TaskDto> getTasksByAuthor(Long authorId, Pageable pageable);
    List<TaskDto> getTasksByAssignee(Long assigneeId, Pageable pageable);
     */
    void updateTaskStatus(Long taskId, TaskStatus status);
    void updateTaskPriority(Long taskId, TaskPriority priority);
    void assignTaskToUser(Long taskId, Long userId);

    TaskDto getTaskById(Long id);
}
