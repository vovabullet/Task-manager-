package com.example.taskproject.controllers;

import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // создание комментария
    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
        return ResponseEntity.ok(commentDto);
    }

    // обновления комментария
    @PutMapping("/{id}/update")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long id, @RequestBody CommentDto commentDto) {
        commentService.updateComment(id, commentDto);
        return ResponseEntity.ok(commentDto);
    }

    // удаление комментария
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // получения комментария по id
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    // получение всех комментариев указанной задачи
    @GetMapping("/byTask/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getAllCommentsByTaskId(id));
    }
}
