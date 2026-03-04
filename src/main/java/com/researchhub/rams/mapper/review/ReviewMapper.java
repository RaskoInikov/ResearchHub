package com.researchhub.rams.mapper.review;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.review.ReviewRequestDto;
import com.researchhub.rams.dto.review.ReviewResponseDto;
import com.researchhub.rams.dto.review.ReviewUpdateDto;
import com.researchhub.rams.entity.review.Review;

@Component
public class ReviewMapper {

    public ReviewResponseDto toResponse(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setComment(review.getComment());
        dto.setArticleId(review.getArticle().getId());
        dto.setReviewerId(review.getReviewer().getId());
        return dto;
    }

    public Review toEntity(ReviewRequestDto dto) {
        Review review = new Review();
        review.setScore(dto.getScore());
        review.setComment(dto.getComment());
        return review;
    }

    public void updateEntity(Review review, ReviewUpdateDto dto) {
        if (dto.getScore() != null) {
            review.setScore(dto.getScore());
        }
        if (dto.getComment() != null) {
            review.setComment(dto.getComment());
        }
    }
}