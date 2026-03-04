package com.researchhub.rams.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.researchhub.rams.entity.review.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByArticleId(UUID articleId);
}