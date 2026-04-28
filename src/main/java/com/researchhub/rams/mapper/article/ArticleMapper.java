package com.researchhub.rams.mapper.article;

import java.util.List;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.dto.tag.TagResponseDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.articletag.ArticleTag;

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

        List<TagResponseDto> tags = article.getArticleTags()
                .stream()
                .map(ArticleTag::getTag)
                .map(tag -> {
                    TagResponseDto t = new TagResponseDto();
                    t.setId(tag.getId());
                    t.setName(tag.getName());
                    t.setDescription(tag.getDescription());
                    return t;
                })
                .toList();

        dto.setTags(tags);

        double avg = article.getReviews()
                .stream()
                .mapToInt(r -> r.getScore())
                .average()
                .orElse(0.0);

        dto.setRating(avg);

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
        if (dto.getStatus() != null) {
            article.setStatus(dto.getStatus());
        }
    }
}