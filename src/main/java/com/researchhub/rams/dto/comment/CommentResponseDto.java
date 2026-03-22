package com.researchhub.rams.dto.comment;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for comment")
public class CommentResponseDto {

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    private String text;
    private UUID articleId;
    private UUID authorId;
}