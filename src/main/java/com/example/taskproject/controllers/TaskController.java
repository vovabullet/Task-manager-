package com.example.taskproject.controllers;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    // создание задачи
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully!");
    }

    // обновление задачи
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok("Task updated successfully!");
    }

    // удаление задачи
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully!");
    }

    // получение задачи по id
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // обновление статуса задачи
    @PutMapping("/{id}/status")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestParam @Valid TaskStatus status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok("Task status updated successfully!");
    }
    
    // обновление приоритета задач
    @PutMapping("/{id}/priority")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> updateTaskPriority(@PathVariable Long id, @RequestParam @Valid TaskPriority priority) {
        taskService.updateTaskPriority(id, priority);
        return ResponseEntity.ok("Task priority updated successfully!");
    }
    
    // назначение задачи пользователю
    @PutMapping("/{taskId}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTaskAssign(@PathVariable Long taskId, @RequestParam Long assigneeId) {
        taskService.assignTaskToUser(taskId, assigneeId);
        return ResponseEntity.ok("User assigned successfully!");
    }

    // создание комментария к задаче
    @PostMapping("/{id}/comments")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> addComment(@PathVariable Long id, @RequestBody @Valid @NotBlank String content, Authentication authentication) {
        commentService.addComment(id, content, authentication);
        return ResponseEntity.ok("Comment added successfully!");
    }

    // получение всех комментариев указанной задачи
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getAllCommentsByTaskId(id));
    }

}
