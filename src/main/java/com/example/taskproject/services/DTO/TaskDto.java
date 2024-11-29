package com.example.taskproject.services.DTO;

import com.example.taskproject.enums.TaskPriority;
import com.example.taskproject.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
    Long id;
    @NotNull
    @Size(max = 50)
    String title;
    @NotNull
    String description;
    @NotNull
    TaskStatus status;
    @NotNull
    TaskPriority priority;
    @NotNull
    Long authorId;
    Long assigneeId;
}