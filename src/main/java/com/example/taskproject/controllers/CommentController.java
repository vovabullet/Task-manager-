package com.example.taskproject.controllers;

import com.example.taskproject.services.CommentService;
import com.example.taskproject.services.DTO.CommentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // создание комментария
    @Operation(summary = "Create a comment", description = "Creates a new comment. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "201", description = "Comment created successfully")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createComment(@RequestBody @Valid CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.ok("Comment created successfully!");
    }

    // обновления комментария
    @Operation(summary = "Update a comment", description = "Updates an existing comment. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateComment(@PathVariable long id, @RequestBody @Valid CommentDto commentDto) {
        commentService.updateComment(id, commentDto);
        return ResponseEntity.ok("Comment updated successfully!");
    }

    // удаление комментария
    @Operation(summary = "Delete a comment", description = "Deletes a comment by its ID. Only accessible by ADMIN users.")
    @ApiResponse(responseCode = "200", description = "Comment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully!");
    }

    // получения комментария по id
    @Operation(summary = "Get a comment by ID", description = "Fetches a comment by its ID.")
    @ApiResponse(responseCode = "200", description = "Comment retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }
}
