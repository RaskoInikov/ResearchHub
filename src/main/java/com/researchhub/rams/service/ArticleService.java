package com.researchhub.rams.service;

import org.springframework.stereotype.Service;

import com.researchhub.rams.dto.ArticleDto;
import com.researchhub.rams.entity.Article;
import com.researchhub.rams.exceptions.ArticleNotFoundException;
import com.researchhub.rams.mapper.ArticleMapper;
import com.researchhub.rams.repository.ArticleRepository;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    private ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public ArticleDto createArticle(ArticleDto dto) {
        Article article = ArticleMapper.toEntity(dto);
        Article saved = repository.save(article);
        return ArticleMapper.toDto(saved);
    }

    public ArticleDto getById(Long id) {
        Article article = repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        return ArticleMapper.toDto(article);
    }

    public ArticleDto getByTitle(String title) {
        Article article = repository.findByTitle(title).orElseThrow(() -> new ArticleNotFoundException(title));
        return ArticleMapper.toDto(article);
    }
}
