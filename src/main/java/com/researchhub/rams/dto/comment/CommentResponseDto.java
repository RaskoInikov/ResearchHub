package com.researchhub.rams.dto.comment;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

    private UUID id;
    private String text;
    private UUID articleId;
    private UUID authorId;
}