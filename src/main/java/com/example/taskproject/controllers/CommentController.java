package com.example.taskproject.controllers;

import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createComment(@RequestBody @Valid CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.ok("Comment created successfully!");
    }

    // обновления комментария
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateComment(@PathVariable long id, @RequestBody @Valid CommentDto commentDto) {
        commentService.updateComment(id, commentDto);
        return ResponseEntity.ok("Comment updated successfully!");
    }

    // удаление комментария
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully!");
    }

    // получения комментария по id
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }
}
