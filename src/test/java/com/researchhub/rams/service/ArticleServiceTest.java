package com.researchhub.rams.service;

import com.researchhub.rams.cache.QueryKey;
import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.article.ArticleStatus;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.TransactionSimulationException;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.article.ArticleMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleMapper mapper;

    private Map<QueryKey, Page<ArticleResponseDto>> cache;

    @InjectMocks
    private ArticleService service;

    private UUID userId;
    private User user;
    private ArticleRequestDto requestDto;
    private ArticleResponseDto responseDto;

    @BeforeEach
    void setup() {
        cache = new HashMap<>();
        service = new ArticleService(articleRepository, userRepository, mapper, cache);

        userId = UUID.randomUUID();

        user = new User();

        requestDto = new ArticleRequestDto();
        requestDto.setAuthorId(userId);
        requestDto.setStatus(ArticleStatus.DRAFT);
        requestDto.setTitle("Test");

        responseDto = new ArticleResponseDto();
    }

    @Test
    void createShouldSaveArticle() {
        Article article = new Article();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(requestDto)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(mapper.toResponse(article)).thenReturn(responseDto);

        ArticleResponseDto result = service.create(requestDto);

        assertThat(result).isEqualTo(responseDto);
        verify(articleRepository).save(article);
    }

    @Test
    void createBulkshouldSaveAllArticles() {
        Article article = new Article();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenReturn(article);
        when(articleRepository.saveAll(any())).thenReturn(List.of(article, article));
        when(mapper.toResponse(any())).thenReturn(responseDto);

        List<ArticleResponseDto> result = service.createBulk(List.of(requestDto, requestDto));

        assertThat(result).hasSize(2);
        verify(articleRepository).saveAll(any());
    }

    @Test
    void createBulkNoTrxshouldPartiallySave() {
        ArticleRequestDto failDto = new ArticleRequestDto();
        failDto.setAuthorId(userId);
        failDto.setStatus(ArticleStatus.DRAFT);
        failDto.setTitle("FAIL");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // IMPORTANT FIX: dynamic article per DTO
        when(mapper.toEntity(any())).thenAnswer(invocation -> {
            Article a = new Article();
            ArticleRequestDto dto = invocation.getArgument(0);
            a.setTitle(dto.getTitle());
            return a;
        });

        when(articleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any())).thenReturn(responseDto);

        List<ArticleRequestDto> list = List.of(requestDto, failDto);

        assertThatThrownBy(() -> service.createBulkNoTrx(list))
                .isInstanceOf(TransactionSimulationException.class);

        verify(articleRepository, atLeastOnce()).save(any());
    }

    @Test
    void createBulkTrxshouldRollbackOnFailure() {
        ArticleRequestDto failDto = new ArticleRequestDto();
        failDto.setAuthorId(userId);
        failDto.setStatus(ArticleStatus.DRAFT);
        failDto.setTitle("FAIL");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(mapper.toEntity(any())).thenAnswer(invocation -> {
            Article a = new Article();
            ArticleRequestDto dto = invocation.getArgument(0);
            a.setTitle(dto.getTitle());
            return a;
        });

        when(articleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<ArticleRequestDto> list = List.of(requestDto, failDto);

        assertThatThrownBy(() -> service.createBulkTrx(list))
                .isInstanceOf(TransactionSimulationException.class);
    }

    @Test
    void createBulkshouldThrowIfUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createBulk(List.of(requestDto)))
                .isInstanceOf(UserNotFoundException.class);
    }
}