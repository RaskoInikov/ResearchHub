package com.researchhub.rams.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.service.ArticleService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> create(
        @Valid @RequestBody ArticleRequestDto dto) {
            return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getById(
        @PathVariable UUID id) {
            return ResponseEntity.ok(service.getById(id));
        }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponseDto>> getByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> update(
        @PathVariable UUID id,
        @Valid @RequestBody ArticleUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }

    @GetMapping("/n-plus-one")
    public ResponseEntity<List<ArticleResponseDto>> getAllWithProblem() {
        return ResponseEntity.ok(service.getAllWithNPlusOne());
    }

    @GetMapping("/optimized")
    public ResponseEntity<List<ArticleResponseDto>> getAllOptimized() {
        return ResponseEntity.ok(service.getAllOptimized());
    }

    @PostMapping("/demo-no-transaction")
    public ResponseEntity<Void> demoNoTransaction(
            @Valid @RequestBody ArticleRequestDto dto) {

        service.createArticleWithRelationsNoTransaction(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/demo-transaction")
    public ResponseEntity<Void> demoTransaction(
            @Valid @RequestBody ArticleRequestDto dto) {

        service.createArticleWithRelationsTransactional(dto);
        return ResponseEntity.ok().build();
    }
}
