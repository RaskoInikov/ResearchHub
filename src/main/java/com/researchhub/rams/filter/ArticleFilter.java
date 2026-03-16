package com.researchhub.rams.filter;

import com.researchhub.rams.entity.article.ArticleStatus;

public class ArticleFilter {

    private String authorName;

    private ArticleStatus status;

    public ArticleFilter() {
    }

    public ArticleFilter(String authorName, ArticleStatus status) {
        this.authorName = authorName;
        this.status = status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }
}