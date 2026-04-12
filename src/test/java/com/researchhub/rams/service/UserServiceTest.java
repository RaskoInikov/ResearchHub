package com.researchhub.rams.service;

import com.researchhub.rams.dto.user.UserRequestDto;
import com.researchhub.rams.dto.user.UserResponseDto;
import com.researchhub.rams.dto.user.UserUpdateDto;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.user.UserMapper;
import com.researchhub.rams.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void createShouldSave() {
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(mapper.toEntity(any())).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(dto);

        Assertions.assertThat(service.create(new UserRequestDto())).isEqualTo(dto);
    }

    @Test
    void getByIdShouldReturn() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(dto);

        Assertions.assertThat(service.getById(id)).isEqualTo(dto);
    }

    @Test
    void getByIdShouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getById(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getAllShouldReturnList() {
        User entity = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(dto);

        Assertions.assertThat(service.getAll()).hasSize(1);
    }

    @Test
    void getByEmailShouldReturn() {
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findByEmail("test@mail.com")).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(dto);

        Assertions.assertThat(service.getByEmail("test@mail.com")).isEqualTo(dto);
    }

    @Test
    void getByEmailShouldThrow() {
        when(repository.findByEmail("test@mail.com")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getByEmail("test@mail.com"))
                .isInstanceOf(java.util.NoSuchElementException.class);
    }

    @Test
    void updateShouldUpdate() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserResponseDto dto = new UserResponseDto();

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(dto);

        Assertions.assertThat(service.update(id, new UserUpdateDto())).isEqualTo(dto);
        verify(mapper).updateEntity(eq(user), any());
    }

    @Test
    void updateShouldThrowWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        UserUpdateDto dto = new UserUpdateDto();

        Assertions.assertThatThrownBy(() -> service.update(id, dto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteShouldCallRepository() {
        UUID id = UUID.randomUUID();
        service.delete(id);
        verify(repository).deleteById(id);
    }
}