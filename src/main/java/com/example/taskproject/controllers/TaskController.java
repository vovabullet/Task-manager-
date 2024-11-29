package com.example.taskproject.controllers;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // создание задачи
    @PostMapping("/create")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.ok(taskDto);
    }

    // обновление задачи
    @PutMapping("/{id}/update")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(taskDto);
    }

    // удаление задачи
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // получение задачи по id
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // обновление статуса задачи
    @PutMapping("/{id}/statusUpdate")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.noContent().build();
    }
    
    // обновление приоритета задач
    @PutMapping("/{id}/priorityUpdate")
    public ResponseEntity<TaskDto> updateTaskPriority(@PathVariable Long id, @RequestParam TaskPriority priority) {
        taskService.updateTaskPriority(id, priority);
        return ResponseEntity.noContent().build();
    }
    
    // назначение задачи пользователю
    @PutMapping("/{taskId}/assign")
    public ResponseEntity<TaskDto> updateTaskAssign(@PathVariable Long taskId, @RequestParam Long assigneeId) {
        taskService.assignTaskToUser(taskId, assigneeId);
        return ResponseEntity.noContent().build();
    }

}
