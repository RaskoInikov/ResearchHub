package com.researchhub.rams.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for tag")
public class TagRequestDto {

    @Schema(example = "Java")
    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;
}