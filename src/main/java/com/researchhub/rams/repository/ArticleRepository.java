package com.researchhub.rams.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.researchhub.rams.entity.article.Article;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Optional<Article> findByTitle(String title);

    List<Article> findAll();

    @EntityGraph(attributePaths = {
            "comments",
            "reviews",
            "articleTags"
    })
    @Query("SELECT a FROM Article a")
    List<Article> findAllWithRelations();
}