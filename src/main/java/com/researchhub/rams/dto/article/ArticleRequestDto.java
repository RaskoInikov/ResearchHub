package com.researchhub.rams.dto.article;

import java.time.Instant;
import java.util.UUID;

import com.researchhub.rams.entity.article.ArticleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequestDto {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String abstractText;

    @NotBlank
    private String content;

    @NotNull
    private UUID authorId;

    private String summary;

    private Instant publicationDate;

    @NotNull
    private ArticleStatus status;
}