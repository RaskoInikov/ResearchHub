package com.researchhub.rams.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.article.ArticleStatus;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Optional<Article> findByTitle(String title);

    @EntityGraph(attributePaths = {
            "articleTags",
            "articleTags.tag",
            "reviews"
    })
    @Query("""
        SELECT a
        FROM Article a
        JOIN a.author au
        WHERE au.username = :authorName
        AND a.status = :status
    """)
    Page<Article> searchWithFilters(
            @Param("authorName") String authorName,
            @Param("status") ArticleStatus status,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
        "articleTags",
        "articleTags.tag",
        "reviews"
    })
    @Query("""
        SELECT a
        FROM Article a
        JOIN a.author au
    """)
    Page<Article> searchAll(Pageable pageable);
}