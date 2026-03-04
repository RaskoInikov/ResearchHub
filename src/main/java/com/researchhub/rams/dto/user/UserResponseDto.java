package com.researchhub.rams.dto.user;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private UUID id;
    private String username;
    private String email;
    private Boolean isActive;
}