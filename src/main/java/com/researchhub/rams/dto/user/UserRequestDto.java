package com.researchhub.rams.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for user")
public class UserRequestDto {

    @Schema(example = "john_doe")
    @NotBlank
    @Size(max = 100)
    private String username;

    @Schema(example = "john@example.com")
    @NotBlank
    @Email
    private String email;

    @Schema(example = "password123")
    @NotBlank
    @Size(min = 6)
    private String password;
}