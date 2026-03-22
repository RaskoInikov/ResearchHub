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
import org.springframework.http.ResponseEntity;

import com.researchhub.rams.dto.tag.TagRequestDto;
import com.researchhub.rams.dto.tag.TagResponseDto;
import com.researchhub.rams.dto.tag.TagUpdateDto;
import com.researchhub.rams.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/tags")
@Tag(name = "Tags", description = "Tag APIs")
public class TagController {

    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @Operation(summary = "Create tag")
    @PostMapping
    public ResponseEntity<TagResponseDto> create(
            @Valid @RequestBody TagRequestDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @Operation(summary = "Get tag by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getById(
            @PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Get all tags")
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Find tag by name")
    @GetMapping("/search")
    public ResponseEntity<TagResponseDto> getByName(
            @RequestParam @NotBlank String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    @Operation(summary = "Update tag")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> update(
            @PathVariable @NotNull UUID id,
            @Valid @RequestBody TagUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotNull UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}