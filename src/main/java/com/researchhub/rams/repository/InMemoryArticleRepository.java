package com.researchhub.rams.repository;

import com.researchhub.rams.entity.Article;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryArticleRepository implements ArticleRepository {
    private Map<Long, Article> storage = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Article save(Article entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return storage.values().stream().filter(article -> article.getTitle().equals(title)).findFirst();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}