package com.example.taskproject.services;

import com.example.taskproject.services.DTO.CommentDto;

import java.util.List;

public interface CommentService {

    // добавление комментария
    void addComment(CommentDto commentDto);

    // получение комментария по id
    CommentDto getCommentById(Long id);

    // возвращение всех комментариев, связанных с задачей, по её ID
    List<CommentDto> getAllCommentsByTaskId(Long taskId);

    // обновление комментария
    void updateComment(Long commentId, CommentDto commentDto);

    // удаления комментария
    void deleteComment(Long commentId);


}
