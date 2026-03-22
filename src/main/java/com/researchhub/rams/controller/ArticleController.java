package com.researchhub.rams.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.filter.ArticleFilter;
import com.researchhub.rams.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/articles")
@Tag(name = "Articles", description = "Article management APIs")
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @Operation(summary = "Create article")
    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(
            @Valid @RequestBody ArticleRequestDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @Operation(summary = "Get article by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getById(
            @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get articles by title")
    @GetMapping("/by-title")
    public ResponseEntity<List<ArticleResponseDto>> getByTitle(
            @RequestParam @NotBlank String title) {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @Operation(summary = "Update article")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody ArticleUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete article")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search articles (JPQL)")
    @GetMapping("/search")
    public Page<ArticleResponseDto> searchArticles(
            ArticleFilter filter,
            Pageable pageable) {
        return service.searchArticles(filter, pageable);
    }

    @Operation(summary = "Search articles (native SQL)")
    @GetMapping("/search-native")
    public Page<ArticleResponseDto> searchArticlesNative(
            ArticleFilter filter,
            Pageable pageable) {
        return service.searchArticlesNative(filter, pageable);
    }
}