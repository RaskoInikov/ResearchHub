package com.researchhub.rams.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.cache.QueryKey;
import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.filter.ArticleFilter;
import com.researchhub.rams.mapper.article.ArticleMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.UserRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper mapper;

    private final Map<QueryKey, Page<ArticleResponseDto>> cache;

    private void invalidateCache() {
        cache.clear();
    }

    public ArticleService(
            ArticleRepository articleRepository,
            UserRepository userRepository,
            ArticleMapper mapper,
            Map<QueryKey, Page<ArticleResponseDto>> cache) {

        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.cache = cache;
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getById(UUID id) {
        return mapper.toResponse(findArticle(id));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getByTitle(String title) {
        return articleRepository.findByTitle(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ArticleResponseDto create(ArticleRequestDto dto) {
        User author = findUser(dto.getAuthorId());
        Article article = buildArticle(dto, author);

        Article saved = articleRepository.save(article);

        invalidateCache();

        return mapper.toResponse(saved);
    }

    public ArticleResponseDto update(UUID id, ArticleUpdateDto dto) {
        Article article = findArticle(id);

        mapper.updateEntity(article, dto);

        Article saved = articleRepository.save(article);

        invalidateCache();

        return mapper.toResponse(saved);
    }

    public void delete(UUID id) {
        articleRepository.deleteById(id);
        invalidateCache();
    }

    private Article findArticle(UUID id) {
        return articleRepository.findById(id).orElseThrow();
    }

    private User findUser(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    private Article buildArticle(ArticleRequestDto dto, User author) {
        Article article = mapper.toEntity(dto);
        article.setAuthor(author);
        article.setStatus(dto.getStatus());
        return article;
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> searchArticles(ArticleFilter filter, Pageable pageable) {

        QueryKey key = new QueryKey(
                filter.getAuthorName(),
                filter.getStatus(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        Page<ArticleResponseDto> result =
                articleRepository.searchArticles(
                        filter.getAuthorName(),
                        filter.getStatus(),
                        pageable
                ).map(mapper::toResponse);

        cache.put(key, result);

        return result;
    }

    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> searchArticlesNative(ArticleFilter filter, Pageable pageable) {

        QueryKey key = new QueryKey(
                filter.getAuthorName(),
                filter.getStatus(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        Page<ArticleResponseDto> result =
                articleRepository.searchArticlesNative(
                        filter.getAuthorName(),
                        filter.getStatus() != null ? filter.getStatus().name() : null,
                        pageable
                ).map(mapper::toResponse);

        cache.put(key, result);

        return result;
    }
}