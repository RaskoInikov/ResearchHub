package com.researchhub.rams.dto.article;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponseDto {

    private UUID id;
    private String title;
    private String abstractText;
    private String summary;
    private String status;
    private Instant publicationDate;
    private UUID authorId;
}