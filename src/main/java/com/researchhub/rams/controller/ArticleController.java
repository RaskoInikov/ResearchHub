package com.researchhub.rams.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.researchhub.rams.dto.ArticleDto;
import com.researchhub.rams.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping
    public ArticleDto create(@RequestBody ArticleDto dto) {
        return service.createArticle(dto);
    }

    @GetMapping("/{id}")
    public ArticleDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public ArticleDto getByTitle(@RequestParam String title) {
        return service.getByTitle(title);
    }
}
