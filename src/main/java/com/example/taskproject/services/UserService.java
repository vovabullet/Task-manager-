package com.example.taskproject.services;

import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.User;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.DTO.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void createUser(UserDto userDto);
    void updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    UserDto getUserById(Long userId);
    UserDto getUserByEmail(String email);

    User getUserByAuthentication(Authentication authentication);

    Page<TaskDto> getTasksByAuthor(Long userId, int page, int size);
    Page<TaskDto> getTasksByAssignee(Long userId, int page, int size);
    void changeUserRole(Long userId, UserRole role);
}
