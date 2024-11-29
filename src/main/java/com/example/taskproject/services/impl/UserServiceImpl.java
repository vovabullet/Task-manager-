package com.example.taskproject.services.impl;

import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.Task;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.CommentRepository;
import com.example.taskproject.repositories.TaskRepository;
import com.example.taskproject.repositories.UserRepository;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.DTO.UserDto;
import com.example.taskproject.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // получение пользователя по id (возвращает модель)
    private User findUserById(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public void createUser(UserDto userDto) {
        // создание пользователя
        User user = new User();
        // передача данных из полученного DTO в нового пользователя
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        // сохранение сущности и получение обновленного объекта
        User savedUser = this.userRepository.save(user);

        // логирование успеха
        logger.info("User with ID {} created successfully", savedUser.getId());
    }

    @Override
    public void updateUser(Long userId, UserDto userDto) {
        // получаю пользователя
        User user = findUserById(userId);

        // обновляю поля
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(userDto.getRole());

        // сохраняю изменения
        User savedUser = userRepository.save(user);

        // логирование успеха
        logger.info("User with ID {} updated successfully", savedUser.getId());
    }

    @Override
    public void deleteUser(Long userId) {
        // получаю пользователя
        User user = findUserById(userId);

        // удаления комментария
        this.userRepository.delete(user);

        // логирование успеха
        logger.info("User with ID {} deleted successfully", userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<TaskDto> getTasksByAuthor(Long userId) {
        // получаю пользователя
        User user = findUserById(userId);

        // получаю список задач, где указанный пользователь является автором
        List<Task> tasks = taskRepository.findAllByAuthorId(user.getId());

        // преобразование сущностей в DTO с использованием Stream API
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByAssignee(Long userId) {
        // получаю пользователя
        User user = findUserById(userId);

        // получаю список задач, где указанный пользователь является исполнителем
        List<Task> tasks = taskRepository.findAllByAssigneeId(user.getId());

        // преобразование сущностей в DTO с использованием Stream API
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getActiveTasksCount(Long userId) {
        // метод должен возвращать кол-во АКТИВНЫХ задач, по этому, с помощью stream(), подсчитывается кол-во задач,
        // где указанный пользователь является исполнителем, и статус задачи - В_ПРОГРЕССЕ.
        return (int) taskRepository.findAllByAssigneeId(userId).stream()
                .filter(task -> task.getStatus().equals(TaskStatus.IN_PROGRESS))
                .count();
    }

    @Override
    public void changeUserRole(Long userId, UserRole role) {
        // получаю пользователя
        User user = findUserById(userId);

        // проверка, установлена ли тот же роль для пользователя, и изменение её в противном случае
        if (!user.getRole().equals(role)) {
            user.setRole(role);
            // сохраняю изменения
            userRepository.saveAndFlush(user);
            logger.info("User with ID {} changed to {}", user.getId(), role);
        } else {
            logger.warn("User is already assigned to role {}", role);
        }
    }
}
