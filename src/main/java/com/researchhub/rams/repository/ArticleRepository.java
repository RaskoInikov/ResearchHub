package com.researchhub.rams.repository;

import java.util.UUID;
import java.util.List;
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
            "comments",
            "reviews",
            "articleTags"
    })
    @Query("SELECT a FROM Article a")
    List<Article> findAllWithRelations();

    @Query("""
        SELECT a
        FROM Article a
        JOIN a.author au
        WHERE (COALESCE(:authorName, au.username) = au.username)
        AND (COALESCE(:status, a.status) = a.status)
    """)
    Page<Article> searchArticles(
            @Param("authorName") String authorName,
            @Param("status") ArticleStatus status,
            Pageable pageable
    );

    @Query(
        value = """
        SELECT a.*
        FROM articles a
        JOIN users u ON a.author_id = u.id
        WHERE (:authorName IS NULL OR u.username = :authorName)
        AND (:status IS NULL OR a.status = CAST(:status AS article_status))
        """,
        countQuery = """
        SELECT count(*)
        FROM articles a
        JOIN users u ON a.author_id = u.id
        WHERE (:authorName IS NULL OR u.username = :authorName)
        AND (:status IS NULL OR a.status = CAST(:status AS article_status))
        """,
        nativeQuery = true
    )
    Page<Article> searchArticlesNative(
            @Param("authorName") String authorName,
            @Param("status") String status,
            Pageable pageable
    );
}