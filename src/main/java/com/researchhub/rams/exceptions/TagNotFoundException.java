package com.researchhub.rams.exceptions;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class TagNotFoundException extends ApiException {
    public TagNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Tag not found with id: " + id);
    }
}