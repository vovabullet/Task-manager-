package com.example.taskproject.controllers;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.User;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.DTO.UserDto;
import com.example.taskproject.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // создание пользователя
    @Operation(summary = "Create a user", description = "Creates a new user. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser (@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User created successfully!");
    }

    // обновление пользователя
    @Operation(summary = "Update a user", description = "Updates an existing user. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser (@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok("User updated successfully!");
    }

    // удаление пользователя
    @Operation(summary = "Delete a user", description = "Deletes a user by its ID. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser (@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    // получения пользователя по id
    @Operation(summary = "Get a user by ID", description = "Fetches a user by its ID.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById (@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // получения пользователя по email
    @Operation(summary = "Get a user by email", description = "Fetches a user by its email.")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail (@RequestParam @Valid @NotBlank String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // получение текущего пользователя
    // TODO добавить остальные аннотации
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        return ResponseEntity.ok(user);
    }

    // получение задач, созданных пользователем
    @Operation(summary = "Get all tasks created by user", description = "Fetches all tasks created by user, with pagination.")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}/tasks/author")
    public ResponseEntity<Page<TaskDto>> getTasksByAuthor (
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.getTasksByAuthor(id, page, size));
    }

    // получение задач, назначенных пользователю
    @Operation(summary = "Get all tasks assigned to a user", description = "Fetches all tasks assigned to a user, with pagination.")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}/tasks/assignee")
    public ResponseEntity<Page<TaskDto>> getTasksByAssignee (
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.getTasksByAssignee(id, page, size));
    }

    // изменение роли пользователя
    @Operation(summary = "Change user role", description = "Change user role. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "Role changed successfully")
    @ApiResponse(responseCode = "404", description = "User or user not found")
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserRole (@PathVariable Long id, @RequestParam UserRole role) {
        userService.changeUserRole(id, role);
        return ResponseEntity.ok("User role updated successfully!");
    }
}
