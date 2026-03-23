package com.researchhub.rams.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.researchhub.rams.dto.comment.CommentRequestDto;
import com.researchhub.rams.dto.comment.CommentResponseDto;
import com.researchhub.rams.dto.comment.CommentUpdateDto;
import com.researchhub.rams.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/comments")
@Tag(name = "Comments", description = "Comment APIs")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @Operation(summary = "Create comment")
    @PostMapping
    public ResponseEntity<CommentResponseDto> create(
            @Valid @RequestBody CommentRequestDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @Operation(summary = "Get comment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getById(
            @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all comments")
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get comments by article")
    @GetMapping("/by-article")
    public ResponseEntity<List<CommentResponseDto>> getByArticle(
            @RequestParam @NotNull UUID articleId) {
        return ResponseEntity.ok(service.getByArticle(articleId));
    }

    @Operation(summary = "Update comment")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody CommentUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}