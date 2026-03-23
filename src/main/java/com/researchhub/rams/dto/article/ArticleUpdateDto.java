package com.researchhub.rams.dto.article;

import java.time.Instant;

import com.researchhub.rams.entity.article.ArticleStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating article")
public class ArticleUpdateDto {

    @Schema(description = "Title", example = "Updated AI article")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Schema(description = "Abstract")
    @Size(max = 1000, message = "Abstract too long")
    private String abstractText;

    @Schema(description = "Full content")
    private String content;

    @Schema(description = "Summary")
    @Size(max = 2000, message = "Summary too long")
    private String summary;

    @Schema(description = "Publication date")
    private Instant publicationDate;

    @Schema(description = "Status", example = "PUBLISHED")
    private ArticleStatus status;
}