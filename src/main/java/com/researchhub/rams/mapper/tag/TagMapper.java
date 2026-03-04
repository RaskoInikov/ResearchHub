package com.researchhub.rams.mapper.tag;

import org.springframework.stereotype.Component;

import com.researchhub.rams.dto.tag.TagRequestDto;
import com.researchhub.rams.dto.tag.TagResponseDto;
import com.researchhub.rams.dto.tag.TagUpdateDto;
import com.researchhub.rams.entity.tag.Tag;

@Component
public class TagMapper {

    public TagResponseDto toResponse(Tag tag) {
        TagResponseDto dto = new TagResponseDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setDescription(tag.getDescription());
        return dto;
    }

    public Tag toEntity(TagRequestDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setDescription(dto.getDescription());
        return tag;
    }

    public void updateEntity(Tag tag, TagUpdateDto dto) {
        if (dto.getName() != null) {
            tag.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            tag.setDescription(dto.getDescription());
        }
    }
}