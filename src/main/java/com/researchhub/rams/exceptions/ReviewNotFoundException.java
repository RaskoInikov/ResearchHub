package com.researchhub.rams.exceptions;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class ReviewNotFoundException extends ApiException {
    public ReviewNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Review not found with id: " + id);
    }
}