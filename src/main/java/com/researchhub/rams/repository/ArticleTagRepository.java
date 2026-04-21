package com.researchhub.rams.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.entity.articletag.ArticleTag;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, UUID> {

    @Transactional
    void deleteByArticleId(UUID articleId);

    @Transactional
    void deleteByTagId(UUID tagId);
}