package com.example.taskproject.models;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Size(max = 50)
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING) // Хранение значения enum в строковом виде
    private TaskStatus status;

    @NotNull
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING) // Хранение значения enum в строковом виде
    private TaskPriority priority;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "assignee_id")
    private User assignee;

}