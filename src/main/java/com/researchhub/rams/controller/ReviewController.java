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

import com.researchhub.rams.dto.review.ReviewRequestDto;
import com.researchhub.rams.dto.review.ReviewResponseDto;
import com.researchhub.rams.dto.review.ReviewUpdateDto;
import com.researchhub.rams.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Review APIs")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @Operation(summary = "Create review")
    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(
            @Valid @RequestBody ReviewRequestDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @Operation(summary = "Get review by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getById(
            @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all reviews")
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get reviews by article")
    @GetMapping("/by-article")
    public ResponseEntity<List<ReviewResponseDto>> getByArticle(
            @RequestParam @NotNull UUID articleId) {
        return ResponseEntity.ok(service.getByArticle(articleId));
    }

    @Operation(summary = "Update review")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody ReviewUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete review")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}