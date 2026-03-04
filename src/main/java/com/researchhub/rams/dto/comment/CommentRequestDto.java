package com.researchhub.rams.dto.comment;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank
    private String text;

    @NotNull
    private UUID articleId;

    @NotNull
    private UUID authorId;
}