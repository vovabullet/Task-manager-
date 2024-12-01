package com.example.taskproject;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import com.example.taskproject.enums.UserRole;
import com.example.taskproject.models.Comment;
import com.example.taskproject.models.Task;
import com.example.taskproject.models.User;
import com.example.taskproject.repositories.CommentRepository;
import com.example.taskproject.repositories.TaskRepository;
import com.example.taskproject.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TaskIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        // создание и сохранение пользователя
        user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setRole(UserRole.ROLE_USER);
        user = userRepository.save(user);

        // создание и сохранение задачи
        task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Task for testing comments");
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(TaskPriority.LOW);
        task.setAuthor(user);
        task.setAssignee(user);
        task = taskRepository.save(task);
    }

    @Test
    void testCreateAndRetrieveComments() {
        // создание комментария
        Comment comment1 = new Comment();
        comment1.setContent("This is the first comment");
        comment1.setAuthor(user);
        comment1.setTask(task);
        commentRepository.save(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("This is the second comment");
        comment2.setAuthor(user);
        comment2.setTask(task);
        commentRepository.save(comment2);

        // настройка пагинации
        Pageable pageable = PageRequest.of(0, 5);
        // получение комментариев по задаче
        List<Comment> comments = commentRepository.findAllByTaskId(task.getId(), pageable).getContent();

        // проверка результатов
        assertEquals(2, comments.size());
        assertEquals("This is the first comment", comments.get(0).getContent());
        assertEquals("This is the second comment", comments.get(1).getContent());

    }
}
