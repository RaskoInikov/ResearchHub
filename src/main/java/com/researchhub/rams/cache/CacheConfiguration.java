package com.researchhub.rams.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import com.researchhub.rams.dto.article.ArticleResponseDto;

@Configuration
public class CacheConfiguration {

    @Bean
    public Map<QueryKey, Page<ArticleResponseDto>> articleSearchCache() {
        return new HashMap<>();
    }

}