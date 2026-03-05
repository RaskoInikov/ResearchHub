package com.researchhub.rams.dto.article;

import java.time.Instant;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateDto {

    @Size(max = 255)
    private String title;

    private String abstractText;

    private String content;

    private String summary;

    private Instant publicationDate;
}