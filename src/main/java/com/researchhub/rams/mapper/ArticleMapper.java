package com.researchhub.rams.mapper;

import com.researchhub.rams.dto.ArticleDto;
import com.researchhub.rams.entity.Article;

public final class ArticleMapper {
    private ArticleMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ArticleDto toDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .abstractText(article.getAbstractText())
                .publicationDate(article.getPublicationDate())
                .build();
    }

    public static Article toEntity(ArticleDto dto) {
        return Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .abstractText(dto.getAbstractText())
                .publicationDate(dto.getPublicationDate())
                .build();
    }

}
