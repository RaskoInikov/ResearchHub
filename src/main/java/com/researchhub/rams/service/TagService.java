package com.researchhub.rams.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.dto.tag.TagRequestDto;
import com.researchhub.rams.dto.tag.TagResponseDto;
import com.researchhub.rams.dto.tag.TagUpdateDto;
import com.researchhub.rams.entity.tag.Tag;
import com.researchhub.rams.exceptions.TagNotFoundException;
import com.researchhub.rams.mapper.tag.TagMapper;
import com.researchhub.rams.repository.TagRepository;

@Service
@Transactional
public class TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    public TagService(TagRepository repository, TagMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TagResponseDto create(TagRequestDto dto) {
        return mapper.toResponse(repository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public TagResponseDto getById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TagResponseDto getByName(String name) {
        return mapper.toResponse(repository.findByName(name).orElseThrow());
    }

    public TagResponseDto update(UUID id, TagUpdateDto dto) {

        Tag tag = repository.findById(id).orElseThrow(() -> new TagNotFoundException(id));

        mapper.updateEntity(tag, dto);

        return mapper.toResponse(repository.save(tag));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}