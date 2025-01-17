package com.example.taskproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String adminHome() {
        return "admin_home";
    }


    @GetMapping("/users")
    public String adminUsers() {
        return "admin_users";
    }

    @GetMapping("/users/add")
    public String addUserForm() {
        return "add_user";
    }
    @GetMapping("/users/edit")
    public String editUserForm() {
        return "edit_user"; // Имя файла HTML
    }


    @GetMapping("/tasks")
    public String adminTasks() {
        return "admin_tasks";
    }

    @GetMapping("/tasks/add")
    public String addTaskForm() {
        return "add_task";
    }

    @GetMapping("/tasks/edit")
    public String editTaskForm() {
        return "add_task";
    }
}

