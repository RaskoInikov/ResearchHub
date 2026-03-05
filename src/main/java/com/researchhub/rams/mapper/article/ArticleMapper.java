package com.researchhub.rams.mapper.article;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.entity.article.Article;

@Component
public class ArticleMapper {

    public ArticleResponseDto toResponse(Article article) {
        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setAbstractText(article.getAbstractText());
        dto.setSummary(article.getSummary());
        dto.setStatus(article.getStatus().name());
        dto.setPublicationDate(article.getPublicationDate());
        dto.setAuthorId(article.getAuthor().getId());
        return dto;
    }

    public Article toEntity(ArticleRequestDto dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setAbstractText(dto.getAbstractText());
        article.setContent(dto.getContent());
        article.setSummary(dto.getSummary());
        article.setPublicationDate(dto.getPublicationDate());
        return article;
    }

    public void updateEntity(Article article, ArticleUpdateDto dto) {
        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }
        if (dto.getAbstractText() != null) {
            article.setAbstractText(dto.getAbstractText());
        }
        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }
        if (dto.getSummary() != null) {
            article.setSummary(dto.getSummary());
        }
        if (dto.getPublicationDate() != null) {
            article.setPublicationDate(dto.getPublicationDate());
        }
    }
}