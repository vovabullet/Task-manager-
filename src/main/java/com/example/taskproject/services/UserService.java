package com.example.taskproject.services;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.DTO.UserDto;

import java.util.List;

public interface UserService {
    void createUser(UserDto userDto);
    void updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    UserDto getUserById(Long userId);
    UserDto getUserByEmail(String email);
    List<TaskDto> getTasksByAuthor(Long userId);
    List<TaskDto> getTasksByAssignee(Long userId);
    int getActiveTasksCount(Long userId);
    void changeUserRole(Long userId, UserRole role);
    /* TODO тут используется пагинация, надо разобраться что это такое
    List<UserDto> getAllUsers(Pageable pageable);
     */
}
