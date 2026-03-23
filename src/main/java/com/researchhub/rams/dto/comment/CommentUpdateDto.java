package com.researchhub.rams.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating comment")
public class CommentUpdateDto {

    @Schema(description = "Updated text", example = "Updated comment")
    @NotBlank
    private String text;
}