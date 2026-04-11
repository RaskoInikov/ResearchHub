package com.researchhub.rams.service;

import com.researchhub.rams.dto.user.UserRequestDto;
import com.researchhub.rams.dto.user.UserResponseDto;
import com.researchhub.rams.dto.user.UserUpdateDto;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.user.UserMapper;
import com.researchhub.rams.repository.UserRepository;

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
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void createShouldSaveUser() {
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(mapper.toEntity(any())).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(dto);

        assertThat(service.create(new UserRequestDto())).isEqualTo(dto);
    }

    @Test
    void getByIdShouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(dto);

        assertThat(service.getById(id)).isEqualTo(dto);
    }

    @Test
    void getByIdShouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateShouldUpdateUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(dto);

        UserResponseDto result = service.update(id, new UserUpdateDto());

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntity(eq(user), any());
    }
}