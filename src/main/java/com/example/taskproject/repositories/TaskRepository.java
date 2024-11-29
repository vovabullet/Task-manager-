package com.example.taskproject.repositories;

import com.example.taskproject.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAuthorId(Long id);

    List<Task> findAllByAssigneeId(Long id);
}