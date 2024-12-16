package com.example.taskproject.controllers;

import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import com.example.taskproject.services.DTO.TaskDto;
import com.example.taskproject.services.TaskService;
import com.example.taskproject.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UserService userService;
    private final TaskService taskService;
    private final CommentService commentService;

    @Autowired
    public HomeController(UserService userService, TaskService taskService, CommentService commentService) {
        this.userService = userService;
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Получение текущего пользователя из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // получения ID пользователя
        Long userId = userService.getUserByEmail(email).getId();

        // Получение задач текущего пользователя
        Page<TaskDto> tasks = userService.getTasksByAssignee(userId, page, size);

        // Передача задач в модель
        model.addAttribute("tasks", tasks.getContent());
        model.addAttribute("totalPages", tasks.getTotalPages());
        model.addAttribute("currentPage", page);

        return "home"; // Отображение шаблона home.html
    }

    // Обработка запроса на получение страницы с подробностями задачи
    @GetMapping("/task/{taskId}")
    public String getTaskDetails(@PathVariable Long taskId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        // Получение данных задачи
        TaskDto task = taskService.getTaskById(taskId);

        // Получение комментариев к задаче с пагинацией
        Page<CommentDto> commentsPage = commentService.getAllCommentsByTaskId(taskId, page, size);

        // Передача данных в модель
        model.addAttribute("task", task);
        model.addAttribute("comments", commentsPage.getContent());
        model.addAttribute("totalPages", commentsPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "task"; // Возвращаем имя шаблона task.html
    }
}
