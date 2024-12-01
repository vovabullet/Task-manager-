package com.example.taskproject.controllers;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Operation(summary = "Create a task", description = "Creates a new task. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully!");
    }

    // обновление задачи
    @Operation(summary = "Update a task", description = "Updates an existing task. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody @Valid TaskDto taskDto) {
        taskService.updateTask(id, taskDto);
        return ResponseEntity.ok("Task updated successfully!");
    }

    // удаление задачи
    @Operation(summary = "Delete a task", description = "Deletes a task by its ID. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully!");
    }

    // получение задачи по id
    @Operation(summary = "Get a task by ID", description = "Fetches a task by its ID.")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // обновление статуса задачи
    @Operation(summary = "Update task status", description = "Updates the status of a task. Accessible by admins and task-related users.")
    @ApiResponse(responseCode = "200", description = "Task status updated successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PutMapping("/{id}/status")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestParam @Valid TaskStatus status) {
        taskService.updateTaskStatus(id, status);
        return ResponseEntity.ok("Task status updated successfully!");
    }
    
    // обновление приоритета задач
    @Operation(summary = "Update task priority", description = "Updates the priority of a task. Accessible by admins and task-related users.")
    @ApiResponse(responseCode = "200", description = "Task priority updated successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PutMapping("/{id}/priority")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> updateTaskPriority(@PathVariable Long id, @RequestParam @Valid TaskPriority priority) {
        taskService.updateTaskPriority(id, priority);
        return ResponseEntity.ok("Task priority updated successfully!");
    }
    
    // назначение задачи пользователю
    @Operation(summary = "Assign a user to a task", description = "Assigns a user to a task. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "User assigned successfully")
    @ApiResponse(responseCode = "404", description = "Task or user not found")
    @PutMapping("/{taskId}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateTaskAssign(@PathVariable Long taskId, @RequestParam Long assigneeId) {
        taskService.assignTaskToUser(taskId, assigneeId);
        return ResponseEntity.ok("User assigned successfully!");
    }

    // создание комментария к задаче
    @Operation(summary = "Add a comment to a task", description = "Adds a comment to a task. Accessible by admins and task-related users.")
    @ApiResponse(responseCode = "200", description = "Comment added successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PostMapping("/{id}/comments")
    @PreAuthorize("@accessService.canChangeTask(#id, authentication)")
    public ResponseEntity<String> addComment(@PathVariable Long id, @RequestBody @Valid @NotBlank String content, Authentication authentication) {
        commentService.addComment(id, content, authentication);
        return ResponseEntity.ok("Comment added successfully!");
    }

    // получение всех комментариев указанной задачи
    @Operation(summary = "Get all comments for a task", description = "Fetches all comments for a task, with pagination.")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentDto>> getCommentsByTaskId(
            @PathVariable long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(commentService.getAllCommentsByTaskId(id, page, size));
    }
}
