package com.researchhub.rams.mapper.comment;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.comment.CommentRequestDto;
import com.researchhub.rams.dto.comment.CommentResponseDto;
import com.researchhub.rams.dto.comment.CommentUpdateDto;
import com.researchhub.rams.entity.comment.Comment;

@Component
public class CommentMapper {

    public CommentResponseDto toResponse(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setArticleId(comment.getArticle().getId());
        dto.setAuthorId(comment.getAuthor().getId());
        return dto;
    }

    public Comment toEntity(CommentRequestDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        return comment;
    }

    public void updateEntity(Comment comment, CommentUpdateDto dto) {
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
    }
}