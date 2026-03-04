package com.researchhub.rams.dto.review;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private UUID id;
    private Integer score;
    private String comment;
    private UUID articleId;
    private UUID reviewerId;
}