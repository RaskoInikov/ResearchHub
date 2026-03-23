package com.researchhub.rams.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating review")
public class ReviewUpdateDto {

    @Schema(description = "Score from 1 to 5", example = "4")
    @Min(1)
    @Max(5)
    private Integer score;

    private String comment;
}