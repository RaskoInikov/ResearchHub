package com.researchhub.rams.exceptions;

import org.springframework.http.HttpStatus;
import java.util.UUID;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "User not found with id: " + id);
    }
}