package com.researchhub.rams.dto.review;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for creating review")
public class ReviewRequestDto {

    @Schema(description = "Score from 1 to 5", example = "5")
    @NotNull(message = "Score is required")
    @Min(value = 1)
    @Max(value = 5)
    private Integer score;

    @Schema(description = "Optional comment")
    @Size(max = 1000)
    private String comment;

    @Schema(description = "Article ID")
    @NotNull
    private UUID articleId;

    @Schema(description = "Reviewer ID")
    @NotNull
    private UUID reviewerId;
}