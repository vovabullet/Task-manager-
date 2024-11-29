package com.example.taskproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskProjectApplication {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode("password"); // Замените "password" на свой пароль
		System.out.println(hashedPassword);
		SpringApplication.run(TaskProjectApplication.class, args);
	}

}
