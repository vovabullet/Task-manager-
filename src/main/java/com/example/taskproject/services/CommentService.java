package com.example.taskproject.services;

import com.example.taskproject.services.DTO.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {

    // создание комментария
    void createComment(CommentDto commentDto);

    // добавление комментария
    void addComment(Long taskId, String content, Authentication authentication);

    // получение комментария по id
    CommentDto getCommentById(Long id);

    // возвращение всех комментариев, связанных с задачей, по её ID
    Page<CommentDto> getAllCommentsByTaskId(Long taskId, int page, int size);

    // обновление комментария
    void updateComment(Long commentId, CommentDto commentDto);

    // удаления комментария
    void deleteComment(Long commentId);


}
