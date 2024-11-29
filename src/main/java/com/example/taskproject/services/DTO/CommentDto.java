package com.example.taskproject.services.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    Long id;
    @NotBlank
    String content;
    @NotNull
    Long authorId;
    @NotNull
    Long taskId;

}