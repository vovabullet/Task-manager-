package com.example.taskproject.services.impl;

import com.example.taskproject.models.Comment;
import com.example.taskproject.models.Task;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.CommentRepository;
import com.example.taskproject.repositories.TaskRepository;
import com.example.taskproject.repositories.UserRepository;
import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    // добавление комментария
    @Override
    public void createComment(CommentDto commentDto) {
        // создание нового комментария
        Comment comment = new Comment();
        // передача данных из полученного DTO в новую задачу
        comment.setContent(commentDto.getContent());
        comment.setTask(taskRepository.findById(commentDto.getTaskId()).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        comment.setAuthor(userRepository.findById(commentDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        comment.setTask(taskRepository.findById(commentDto.getTaskId()).orElseThrow(() -> new EntityNotFoundException("Task not found")));

        // сохранение сущности
        Comment savedComment = commentRepository.save(comment);

        // логирование успеха
        logger.info("Comment with ID {} added successfully", savedComment.getId());
    }

    @Override
    public void addComment(Long taskId, String content, Authentication authentication) {
        // получение текущего пользователя
        String currentUserEmail = authentication.getName();

        // создание нового комментария
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setTask(taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task with ID " + taskId + " not found")));
        comment.setAuthor(userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new EntityNotFoundException("User with email " + currentUserEmail + " not found")));

        // Сохраняем комментарий
        commentRepository.save(comment);
        logger.info("Comment added successfully by user '{}' to task with ID {}", currentUserEmail, taskId);
    }

    // обновление комментария
    @Override
    public void updateComment(Long commentId, CommentDto commentDto) {
        // создание нового комментария
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // обновление полей
        comment.setContent(commentDto.getContent());
        comment.setTask(taskRepository.findById(commentDto.getTaskId()).orElseThrow(() -> new EntityNotFoundException("Task not found")));
        comment.setAuthor(userRepository.findById(commentDto.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("User not found")));
        comment.setTask(taskRepository.findById(commentDto.getTaskId()).orElseThrow(() -> new EntityNotFoundException("Task not found")));

        // сохраняю изменения
        Comment savedComment = commentRepository.save(comment);

        // логирую успех
        logger.info("Comment with ID {} updated successfully", savedComment.getId());
    }

    // удаление комментария
    @Override
    public void deleteComment(Long commentId) {
        // проверка существования комментария
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // удаления комментария
        commentRepository.delete(comment);

        // логирование успех
        logger.info("Comment with ID {} deleted successfully", commentId);
    }

    // получение комментария по id
    @Override
    public CommentDto getCommentById(Long id) {
        return modelMapper.map(commentRepository.findById(id), CommentDto.class);
    }

    // получение всех комментариев указанной задачи
    @Override
    public List<CommentDto> getAllCommentsByTaskId(Long taskId) {
        // получение списка комментариев по ID задачи
        List<Comment> comments = commentRepository.findAllByTaskId(taskId);

        // преобразование сущностей в DTO с использованием Stream API
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }




}
