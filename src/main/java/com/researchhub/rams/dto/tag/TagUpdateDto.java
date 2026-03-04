package com.researchhub.rams.dto.tag;

import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateDto {

    @Size(max = 100)
    private String name;

    private String description;
}