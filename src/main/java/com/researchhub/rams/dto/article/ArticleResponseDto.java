package com.researchhub.rams.dto.article;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.researchhub.rams.dto.tag.TagResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for article")
public class ArticleResponseDto {

    private UUID id;

    private String title;
    private String abstractText;
    private String summary;
    private String status;
    private Instant publicationDate;
    private UUID authorId;

    private List<TagResponseDto> tags;

    private Double rating;
}