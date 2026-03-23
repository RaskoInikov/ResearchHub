package com.researchhub.rams.dto.review;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for review")
public class ReviewResponseDto {

    private UUID id;
    private Integer score;
    private String comment;
    private UUID articleId;
    private UUID reviewerId;
}