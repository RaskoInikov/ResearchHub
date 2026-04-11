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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repository;

    @Mock
    private TagMapper mapper;

    @InjectMocks
    private TagService service;

    @Test
    void createShouldSave() {
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(mapper.toEntity(any())).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.create(new TagRequestDto())).isEqualTo(dto);
    }

    @Test
    void getByIdShouldReturn() {
        UUID id = UUID.randomUUID();
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(tag));
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.getById(id)).isEqualTo(dto);
    }

    @Test
    void getByIdShouldThrow() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    @Test
    void getAllShouldReturnList() {
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(repository.findAll()).thenReturn(List.of(tag));
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.getAll()).hasSize(1);
    }

    @Test
    void getByNameShouldReturn() {
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(repository.findByName("test")).thenReturn(Optional.of(tag));
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.getByName("test")).isEqualTo(dto);
    }

    @Test
    void getByNameShouldThrow() {
        when(repository.findByName("test")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByName("test"))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    @Test
    void updateShouldUpdate() {
        UUID id = UUID.randomUUID();
        Tag tag = new Tag();
        TagResponseDto dto = new TagResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(tag));
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toResponse(tag)).thenReturn(dto);

        assertThat(service.update(id, new TagUpdateDto())).isEqualTo(dto);
        verify(mapper).updateEntity(eq(tag), any());
    }

    @Test
    void updateShouldThrow() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, new TagUpdateDto()))
                .isInstanceOf(TagNotFoundException.class);
    }

    @Test
    void deleteShouldCallRepository() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        verify(repository).deleteById(id);
    }
}