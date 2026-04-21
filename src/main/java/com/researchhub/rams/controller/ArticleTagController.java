package com.researchhub.rams.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.service.ArticleTagService;

@RestController
@RequestMapping("/article-tags")
public class ArticleTagController {

    private final ArticleTagService service;

    public ArticleTagController(ArticleTagService service) {
        this.service = service;
    }

    @PutMapping("/{articleId}")
    public void updateTags(
            @PathVariable UUID articleId,
            @RequestBody Set<UUID> tagIds
    ) {
        service.updateTags(articleId, tagIds);
    }
}