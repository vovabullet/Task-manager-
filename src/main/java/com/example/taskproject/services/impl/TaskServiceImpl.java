package com.example.taskproject.services.impl;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.models.Task;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.CommentRepository;
import com.example.taskproject.repositories.TaskRepository;
import com.example.taskproject.repositories.UserRepository;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository repository, UserRepository userRepository, ModelMapper modelMapper) {
        this.taskRepository = repository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // получение задачи по id (возвращает модель)
    private Task findTaskById(Long taskId) {
        return this.taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    // создание задачи
    @Override
    public void createTask(TaskDto taskDto) {
        // проверка, является ли автор 
        // создание новой задачи
        Task task = new Task();
        // передача данных из полученного DTO в новую задачу
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.PENDING); // задача создаётся со статусом "в ожидании"
        task.setPriority(taskDto.getPriority());
        task.setAuthor(userRepository.findById(taskDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found")));
        task.setAssignee(userRepository.findById(taskDto.getAssigneeId()).orElseThrow(() -> new EntityNotFoundException("Assignee not found")));

        // сохранение сущности
        Task savedTask = taskRepository.save(task);

        // логирование успеха
        logger.info("Task with ID {} '{}' created successfully", savedTask.getId(), savedTask.getTitle());
    }

    // обновление задачи
    @Override
    public void updateTask(Long taskId, TaskDto taskDto) {
        // получение задачи
        Task task = findTaskById(taskId);

        // обновление полей
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setAuthor(userRepository.findById(taskDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("Author not found")));
        task.setAssignee(userRepository.findById(taskDto.getAssigneeId()).orElseThrow(() -> new EntityNotFoundException("Assignee not found")));

        // сохранение изменений
        Task savedTask = taskRepository.save(task);

        // логирование успеха
        logger.info("Task with ID {} '{}' updated successfully", savedTask.getId(), savedTask.getTitle());
    }

    // удаление задачи
    @Override
    public void deleteTask(Long taskId) {
        // получаю задачу
        Task task = findTaskById(taskId);

        // удаления комментария
        taskRepository.delete(task);

        // логирование успеха
        logger.info("Task with ID {} '{}' deleted successfully", taskId, task.getTitle());
    }

    // обновление статуса задачи
    @Override
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        // получаю задачу
        Task task = findTaskById(taskId);

        // проверка, установлен ли тот же статус
        if (!task.getStatus().equals(status)) {
            task.setStatus(status);
            // сохранение изменений
            taskRepository.save(task);
            // логирование успеха
            logger.info("Task with ID {} '{}' status changed successfully", taskId, task.getTitle());
        } else {
            logger.warn("Task with ID {} '{}' already has status '{}'", taskId, task.getTitle(), status);
        }
    }

    // обновление приоритета задачи
    @Override
    public void updateTaskPriority(Long taskId, TaskPriority priority) {
        // получаю задачу
        Task task = findTaskById(taskId);

        // проверка, установлен ли тот же приоритет на задаче
        if (!task.getPriority().equals(priority)) {
            task.setPriority(priority);
            // сохраняю изменения
            taskRepository.save(task);
            logger.info("Task with ID {} '{}' priority changed successfully", taskId, task.getTitle());
        } else {
            logger.warn("Task with ID {} '{}' already has priority '{}'", taskId, task.getTitle(), priority);
        }
    }

    // получение задачи по id (возвращает DTO)
    @Override
    public TaskDto getTaskById(Long id) {
        return modelMapper.map(findTaskById(id), TaskDto.class);
    }

    // назначить задачу пользователю
    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        // получаю задачу
        Task task = findTaskById(taskId);
        // получение исполнителя
        User assignee = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Assignee not found"));
        // проверка, назначен ли исполнитель на задачу
        if (!task.getAssignee().equals(assignee)) {
            task.setAssignee(assignee);
            // сохраняю изменения
            taskRepository.save(task);
            logger.info("Task with ID {} '{}' assigned to user '{}'", taskId, task.getTitle(), userId);
        } else {
            logger.warn("Task with ID {} '{}' already has assigned to user '{}'", taskId, task.getTitle(), userId);
        }
    }
}
