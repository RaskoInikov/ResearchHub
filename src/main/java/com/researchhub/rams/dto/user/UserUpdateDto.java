package com.researchhub.rams.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    @Size(max = 100)
    private String username;

    @Email
    private String email;

    private Boolean isActive;
}