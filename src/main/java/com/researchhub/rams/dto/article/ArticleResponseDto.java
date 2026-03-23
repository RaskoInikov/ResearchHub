package com.researchhub.rams.dto.article;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for article")
public class ArticleResponseDto {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    private String title;
    private String abstractText;
    private String summary;
    private String status;
    private Instant publicationDate;
    private UUID authorId;
}