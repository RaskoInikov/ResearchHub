package com.researchhub.rams.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateDto {

    @Min(1)
    @Max(5)
    private Integer score;

    private String comment;
}