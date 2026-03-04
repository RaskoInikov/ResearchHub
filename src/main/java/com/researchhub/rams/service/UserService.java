package com.researchhub.rams.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.dto.user.UserRequestDto;
import com.researchhub.rams.dto.user.UserResponseDto;
import com.researchhub.rams.dto.user.UserUpdateDto;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.mapper.user.UserMapper;
import com.researchhub.rams.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserResponseDto create(UserRequestDto dto) {
        return mapper.toResponse(repository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getByEmail(String email) {
        return mapper.toResponse(repository.findByEmail(email).orElseThrow());
    }

    public UserResponseDto update(UUID id, UserUpdateDto dto) {

        User user = repository.findById(id).orElseThrow();

        mapper.updateEntity(user, dto);

        return mapper.toResponse(repository.save(user));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}