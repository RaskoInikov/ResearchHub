package com.researchhub.rams.exceptions;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ArticleNotFoundException extends ApiException {

    public ArticleNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Article not found with id: " + id);
    }

    public ArticleNotFoundException(String title) {
        super(HttpStatus.NOT_FOUND, "Article not found with title: " + title);
    }
}