package com.researchhub.rams.service;

import com.researchhub.rams.dto.review.ReviewRequestDto;
import com.researchhub.rams.dto.review.ReviewResponseDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.review.Review;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.ArticleNotFoundException;
import com.researchhub.rams.exceptions.ReviewNotFoundException;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.review.ReviewMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.ReviewRepository;
import com.researchhub.rams.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper mapper;

    @InjectMocks
    private ReviewService service;

    private UUID articleId;
    private UUID userId;
    private UUID reviewId;

    private Article article;
    private User user;
    private Review review;
    private ReviewRequestDto requestDto;
    private ReviewResponseDto responseDto;

    @BeforeEach
    void setup() {
        articleId = UUID.randomUUID();
        userId = UUID.randomUUID();
        reviewId = UUID.randomUUID();

        article = new Article();
        user = new User();
        review = new Review();

        requestDto = new ReviewRequestDto();
        requestDto.setArticleId(articleId);
        requestDto.setReviewerId(userId);

        responseDto = new ReviewResponseDto();
    }

    @Test
    void createShouldSaveReview() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(requestDto)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(mapper.toResponse(review)).thenReturn(responseDto);

        ReviewResponseDto result = service.create(requestDto);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void createShouldThrowIfArticleNotFound() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(requestDto))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @Test
    void createShouldThrowIfUserNotFound() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(requestDto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getByIdShouldReturnReview() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(mapper.toResponse(review)).thenReturn(responseDto);

        assertThat(service.getById(reviewId)).isEqualTo(responseDto);
    }

    @Test
    void getByIdShouldThrowIfNotFound() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(reviewId))
                .isInstanceOf(ReviewNotFoundException.class);
    }
}