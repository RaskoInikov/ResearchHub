package com.researchhub.rams.service;

import com.researchhub.rams.dto.tag.TagRequestDto;
import com.researchhub.rams.dto.tag.TagResponseDto;
import com.researchhub.rams.dto.tag.TagUpdateDto;
import com.researchhub.rams.entity.tag.Tag;
import com.researchhub.rams.exceptions.TagNotFoundException;
import com.researchhub.rams.mapper.tag.TagMapper;
import com.researchhub.rams.repository.TagRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repository;

    @Mock
    private TagMapper mapper;

    @InjectMocks
    private TagService service;

    @Test
    void createShouldSaveTag() {
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(mapper.toEntity(any())).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.create(new TagRequestDto())).isEqualTo(dto);
    }

    @Test
    void updateShouldUpdateTag() {
        UUID id = UUID.randomUUID();
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(tag));
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toResponse(tag)).thenReturn(dto);

        TagResponseDto result = service.update(id, new TagUpdateDto());

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntity(eq(tag), any());
    }

    @Test
    void updateShouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, new TagUpdateDto()))
                .isInstanceOf(TagNotFoundException.class);
    }
}