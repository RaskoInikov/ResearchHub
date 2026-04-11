package com.researchhub.rams.service;

import com.researchhub.rams.cache.QueryKey;
import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.article.ArticleStatus;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.ArticleNotFoundException;
import com.researchhub.rams.exceptions.TransactionSimulationException;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.filter.ArticleFilter;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private UUID articleId;
    private User user;
    private Article article;
    private ArticleRequestDto requestDto;
    private ArticleResponseDto responseDto;

    @BeforeEach
    void setup() {
        cache = new HashMap<>();
        service = new ArticleService(articleRepository, userRepository, mapper, cache);

        userId = UUID.randomUUID();
        articleId = UUID.randomUUID();

        user = new User();
        article = new Article();

        requestDto = new ArticleRequestDto();
        requestDto.setAuthorId(userId);
        requestDto.setStatus(ArticleStatus.DRAFT);
        requestDto.setTitle("Test");

        responseDto = new ArticleResponseDto();
    }

    @Test
    void createShouldSaveArticle() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(requestDto)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(mapper.toResponse(article)).thenReturn(responseDto);

        assertThat(service.create(requestDto)).isEqualTo(responseDto);
    }

    @Test
    void getByIdShouldReturnArticle() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(mapper.toResponse(article)).thenReturn(responseDto);

        assertThat(service.getById(articleId)).isEqualTo(responseDto);
    }

    @Test
    void getByIdShouldThrow() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(articleId))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @Test
    void getByTitleShouldReturnList() {
        when(articleRepository.findByTitle("Test")).thenReturn(Optional.of(article));
        when(mapper.toResponse(article)).thenReturn(responseDto);

        List<ArticleResponseDto> result = service.getByTitle("Test");

        assertThat(result).hasSize(1);
    }

    @Test
    void updateShouldUpdateArticle() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);
        when(mapper.toResponse(article)).thenReturn(responseDto);

        assertThat(service.update(articleId, new ArticleUpdateDto())).isEqualTo(responseDto);
        verify(mapper).updateEntity(eq(article), any());
    }

    @Test
    void deleteShouldCallRepository() {
        service.delete(articleId);
        verify(articleRepository).deleteById(articleId);
    }

    @Test
    void createBulkShouldSaveAll() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenReturn(article);
        when(articleRepository.saveAll(any())).thenReturn(List.of(article));
        when(mapper.toResponse(article)).thenReturn(responseDto);

        assertThat(service.createBulk(List.of(requestDto))).hasSize(1);
    }

    @Test
    void createBulkShouldThrowIfUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createBulk(List.of(requestDto)))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void createBulkNoTrxShouldThrow() {
        ArticleRequestDto fail = new ArticleRequestDto();
        fail.setAuthorId(userId);
        fail.setTitle("FAIL");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenAnswer(i -> {
            Article a = new Article();
            a.setTitle(((ArticleRequestDto) i.getArgument(0)).getTitle());
            return a;
        });

        assertThatThrownBy(() -> service.createBulkNoTrx(List.of(requestDto, fail)))
                .isInstanceOf(TransactionSimulationException.class);
    }

    @Test
    void createBulkNoTrxShouldSucceed() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenReturn(article);
        when(articleRepository.save(any())).thenReturn(article);
        when(mapper.toResponse(article)).thenReturn(responseDto);

        List<ArticleResponseDto> result = service.createBulkNoTrx(List.of(requestDto));

        assertThat(result).hasSize(1);
    }

    @Test
    void createBulkTrxShouldThrow() {
        ArticleRequestDto fail = new ArticleRequestDto();
        fail.setAuthorId(userId);
        fail.setTitle("FAIL");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenAnswer(i -> {
            Article a = new Article();
            a.setTitle(((ArticleRequestDto) i.getArgument(0)).getTitle());
            return a;
        });

        assertThatThrownBy(() -> service.createBulkTrx(List.of(requestDto, fail)))
                .isInstanceOf(TransactionSimulationException.class);
    }

    @Test
    void createBulkTrxShouldSucceed() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(any())).thenReturn(article);
        when(articleRepository.save(any())).thenReturn(article);
        when(mapper.toResponse(article)).thenReturn(responseDto);

        List<ArticleResponseDto> result = service.createBulkTrx(List.of(requestDto));

        assertThat(result).hasSize(1);
    }

    @Test
    void searchArticlesShouldCache() {
        ArticleFilter filter = new ArticleFilter();
        PageRequest pageable = PageRequest.of(0, 10);

        when(articleRepository.searchArticles(any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(article)));
        when(mapper.toResponse(article)).thenReturn(responseDto);

        Page<ArticleResponseDto> first = service.searchArticles(filter, pageable);
        Page<ArticleResponseDto> second = service.searchArticles(filter, pageable);

        assertThat(first).isEqualTo(second);
    }

    @Test
    void searchArticlesNativeShouldCacheAndHit() {
        ArticleFilter filter = new ArticleFilter();
        PageRequest pageable = PageRequest.of(0, 10);

        when(articleRepository.searchArticlesNative(any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(article)));
        when(mapper.toResponse(article)).thenReturn(responseDto);

        Page<ArticleResponseDto> first = service.searchArticlesNative(filter, pageable);
        Page<ArticleResponseDto> second = service.searchArticlesNative(filter, pageable);

        assertThat(first).isEqualTo(second);
    }

    @Test
    void createBulkInternalShouldThrowWhenUserMissingInsideStream() {
        ArticleRequestDto dto = new ArticleRequestDto();
        dto.setAuthorId(userId);
        dto.setTitle("OK");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createBulkNoTrx(List.of(dto)))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void searchArticlesNativeShouldHandleNullStatus() {
        ArticleFilter filter = new ArticleFilter();
        filter.setStatus(null);

        PageRequest pageable = PageRequest.of(0, 10);

        when(articleRepository.searchArticlesNative(any(), eq(null), any()))
                .thenReturn(new PageImpl<>(List.of(article)));

        when(mapper.toResponse(article)).thenReturn(responseDto);

        Page<ArticleResponseDto> result = service.searchArticlesNative(filter, pageable);

        assertThat(result).hasSize(1);
    }

    @Test
    void searchArticlesNativeShouldHandleNonNullStatus() {
        ArticleFilter filter = new ArticleFilter();
        filter.setStatus(ArticleStatus.DRAFT);

        PageRequest pageable = PageRequest.of(0, 10);

        when(articleRepository.searchArticlesNative(any(), eq("DRAFT"), any()))
                .thenReturn(new PageImpl<>(List.of(article)));

        when(mapper.toResponse(article)).thenReturn(responseDto);

        Page<ArticleResponseDto> result = service.searchArticlesNative(filter, pageable);

        assertThat(result).hasSize(1);
    }
}