package com.researchhub.rams.dto.article;

import java.time.Instant;
import java.util.UUID;

import com.researchhub.rams.entity.article.ArticleStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for creating an article")
public class ArticleRequestDto {

    @Schema(description = "Article title", example = "AI in 2026")
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Schema(description = "Short abstract", example = "Overview of AI trends")
    @NotBlank(message = "Abstract must not be blank")
    @Size(max = 1000, message = "Abstract too long")
    private String abstractText;

    @Schema(description = "Full article content")
    @NotBlank(message = "Content must not be blank")
    private String content;

    @Schema(description = "Author ID", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "AuthorId is required")
    private UUID authorId;

    @Schema(description = "Short summary")
    @Size(max = 2000, message = "Summary too long")
    private String summary;

    @Schema(description = "Publication date")
    private Instant publicationDate;

    @Schema(description = "Article status", example = "DRAFT")
    @NotNull(message = "Status is required")
    private ArticleStatus status;
}