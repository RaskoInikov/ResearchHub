package com.researchhub.rams.exceptions;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class CommentNotFoundException extends ApiException {
    public CommentNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Comment not found with id: " + id);
    }
}