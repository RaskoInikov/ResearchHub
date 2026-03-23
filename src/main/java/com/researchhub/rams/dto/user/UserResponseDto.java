package com.researchhub.rams.dto.user;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for user")
public class UserResponseDto {

    private UUID id;
    private String username;
    private String email;
    private Boolean isActive;
}