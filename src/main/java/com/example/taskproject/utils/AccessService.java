package com.example.taskproject.utils;

import com.example.taskproject.repositories.CommentRepository;
import com.example.taskproject.repositories.TaskRepository;
import com.example.taskproject.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccessService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public AccessService(TaskRepository taskRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public boolean canChangeTask (Long taskId, Authentication authentication) {
        // получение текущего пользователя
        String currentUserEmail = authentication.getName();

        // проверка роли
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return true; // админ имеет доступ
        }

        // проверка связи задачи с пользователем
        return taskRepository.findById(taskId).map(task -> task.getAssignee().getEmail().equals(currentUserEmail)).orElse(false);
    }
}
