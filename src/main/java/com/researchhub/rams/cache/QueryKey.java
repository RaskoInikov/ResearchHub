package com.researchhub.rams.cache;

import com.researchhub.rams.entity.article.ArticleStatus;

public record QueryKey(
        String authorName,
        ArticleStatus status,
        int page,
        int size
) {
    
}