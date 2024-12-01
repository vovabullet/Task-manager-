package com.example.taskproject.repositories;

import com.example.taskproject.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthorId(Long id, Pageable pageable );

    Page<Task> findAllByAssigneeId(Long id, Pageable pageable);
}