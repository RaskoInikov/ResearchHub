package com.researchhub.rams.repository;

import com.researchhub.rams.entity.*;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends Repository<Article, Long> {
    Optional<Article> findByTitle(String title);

    List<Article> findAll();
}
