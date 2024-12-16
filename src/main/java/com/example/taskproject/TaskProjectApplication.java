package com.example.taskproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TaskProjectApplication {
	public static void main(String[] args) {

		SpringApplication.run(TaskProjectApplication.class, args);
	}


}
