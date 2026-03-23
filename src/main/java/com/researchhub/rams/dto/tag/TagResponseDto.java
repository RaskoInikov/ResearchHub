package com.researchhub.rams.dto.tag;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response DTO for tag")
public class TagResponseDto {

    private UUID id;
    private String name;
    private String description;
}