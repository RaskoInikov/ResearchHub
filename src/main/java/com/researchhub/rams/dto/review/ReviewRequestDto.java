package com.researchhub.rams.dto.review;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    private String comment;

    @NotNull
    private UUID articleId;

    @NotNull
    private UUID reviewerId;
}