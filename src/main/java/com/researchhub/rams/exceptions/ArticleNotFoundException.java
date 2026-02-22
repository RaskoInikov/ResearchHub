package com.researchhub.rams.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Article not found with id: " + id);
    }

    public ArticleNotFoundException(String title) {
        super("Article not found with title: " + title);
    }
}
