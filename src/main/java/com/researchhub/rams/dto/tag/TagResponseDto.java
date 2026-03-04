package com.researchhub.rams.dto.tag;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponseDto {

    private UUID id;
    private String name;
    private String description;
}