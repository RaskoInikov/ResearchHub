package com.researchhub.rams.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.entity.review.Review;
import com.researchhub.rams.dto.review.ReviewRequestDto;
import com.researchhub.rams.dto.review.ReviewResponseDto;
import com.researchhub.rams.dto.review.ReviewUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.mapper.review.ReviewMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.ReviewRepository;
import com.researchhub.rams.repository.UserRepository;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ReviewMapper mapper;

    public ReviewService(
            ReviewRepository reviewRepository,
            ArticleRepository articleRepository,
            UserRepository userRepository,
            ReviewMapper mapper) {

        this.reviewRepository = reviewRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public ReviewResponseDto create(ReviewRequestDto dto) {

        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow();

        User reviewer = userRepository.findById(dto.getReviewerId())
                .orElseThrow();

        Review review = mapper.toEntity(dto);
        review.setArticle(article);
        review.setReviewer(reviewer);

        return mapper.toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getById(UUID id) {
        return mapper.toResponse(reviewRepository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAll() {
        return reviewRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getByArticle(UUID articleId) {
        return reviewRepository.findByArticleId(articleId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ReviewResponseDto update(UUID id, ReviewUpdateDto dto) {

        Review review = reviewRepository.findById(id)
                .orElseThrow();

        mapper.updateEntity(review, dto);

        return mapper.toResponse(reviewRepository.save(review));
    }

    public void delete(UUID id) {
        reviewRepository.deleteById(id);
    }
}