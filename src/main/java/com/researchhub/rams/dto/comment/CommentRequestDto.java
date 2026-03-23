package com.researchhub.rams.dto.comment;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for creating a comment")
public class CommentRequestDto {

    @Schema(description = "Comment text", example = "Great article!")
    @NotBlank(message = "Text must not be blank")
    @Size(max = 1000, message = "Comment too long")
    private String text;

    @Schema(description = "Article ID", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "ArticleId is required")
    private UUID articleId;

    @Schema(description = "Author ID", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "AuthorId is required")
    private UUID authorId;
}