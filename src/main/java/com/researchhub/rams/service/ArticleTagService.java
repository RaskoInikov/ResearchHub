package com.researchhub.rams.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.articletag.ArticleTag;
import com.researchhub.rams.entity.tag.Tag;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.ArticleTagRepository;
import com.researchhub.rams.repository.TagRepository;

@Service
@Transactional
public class ArticleTagService {

    private final ArticleTagRepository articleTagRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleTagService(
            ArticleRepository articleRepository,
            TagRepository tagRepository,
            ArticleTagRepository articleTagRepository
    ) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
    }

    @Transactional
    public void updateTags(UUID articleId, Set<UUID> tagIds) {

        Article article = articleRepository.findById(articleId).orElseThrow();

        articleTagRepository.deleteByArticleId(articleId);
        articleTagRepository.flush();

        Set<ArticleTag> newLinks = tagIds.stream()
                .map(tagId -> {
                    Tag tag = tagRepository.findById(tagId).orElseThrow();

                    ArticleTag at = new ArticleTag();
                    at.setArticle(article);
                    at.setTag(tag);

                    return at;
                })
                .collect(Collectors.toSet());

        article.getArticleTags().clear();
        article.getArticleTags().addAll(newLinks);
    }
}