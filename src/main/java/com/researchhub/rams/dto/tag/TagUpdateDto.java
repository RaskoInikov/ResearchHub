package com.researchhub.rams.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for updating tag")
public class TagUpdateDto {

    @Size(max = 100)
    private String name;

    private String description;
}