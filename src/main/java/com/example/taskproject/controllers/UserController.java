package com.example.taskproject.controllers;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.DTO.UserDto;
import com.example.taskproject.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // создание пользователя
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok(userDto);
    }

    // обновление пользователя
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser (@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return ResponseEntity.ok(userDto);
    }

    // удаление пользователя
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<UserDto> deleteUser (@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // получения пользователя по id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById (@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // получения пользователя по email
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail (@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // получение кол-ва активных задач пользователя
    @GetMapping("{id}/tasks")
    public ResponseEntity<Integer> getActiveTasksCount (@PathVariable Long id) {
        return ResponseEntity.ok(userService.getActiveTasksCount(id));
    }

    // получение задач, созданных пользователем
    @GetMapping("/{id}/tasks/author")
    public ResponseEntity<List<TaskDto>> getTasksByAuthor (@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTasksByAuthor(id));
    }

    // получение задач, назначенных пользователю
    @GetMapping("/{id}/tasks/assignee")
    public ResponseEntity<List<TaskDto>> getTasksByAssignee (@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTasksByAssignee(id));
    }

    // изменение роли пользователя
    @PutMapping("/{id}/changeRole")
    public ResponseEntity<Void> updateUserRole (@PathVariable Long id, @RequestParam UserRole role) {
        userService.changeUserRole(id, role);
        return ResponseEntity.noContent().build();
    }
}
