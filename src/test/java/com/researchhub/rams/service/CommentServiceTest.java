package com.researchhub.rams.service;

import com.researchhub.rams.dto.comment.CommentRequestDto;
import com.researchhub.rams.dto.comment.CommentResponseDto;
import com.researchhub.rams.dto.comment.CommentUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.comment.Comment;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.ArticleNotFoundException;
import com.researchhub.rams.exceptions.CommentNotFoundException;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.comment.CommentMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.CommentRepository;
import com.researchhub.rams.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentService service;

    private UUID articleId;
    private UUID userId;
    private UUID commentId;

    private Article article;
    private User user;
    private Comment comment;
    private CommentRequestDto requestDto;
    private CommentResponseDto responseDto;

    @BeforeEach
    void setup() {
        articleId = UUID.randomUUID();
        userId = UUID.randomUUID();
        commentId = UUID.randomUUID();

        article = new Article();
        user = new User();
        comment = new Comment();

        requestDto = new CommentRequestDto();
        requestDto.setArticleId(articleId);
        requestDto.setAuthorId(userId);

        responseDto = new CommentResponseDto();
    }

    @Test
    void createShouldSaveComment() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toEntity(requestDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(mapper.toResponse(comment)).thenReturn(responseDto);

        CommentResponseDto result = service.create(requestDto);

        Assertions.assertThat(result).isEqualTo(responseDto);
        verify(commentRepository).save(comment);
    }

    @Test
    void createShouldThrowIfArticleNotFound() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.create(requestDto))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @Test
    void createShouldThrowIfUserNotFound() {
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.create(requestDto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void getByIdShouldReturnComment() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(mapper.toResponse(comment)).thenReturn(responseDto);

        CommentResponseDto result = service.getById(commentId);

        Assertions.assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getByIdShouldThrowIfNotFound() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getById(commentId))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    void updateShouldUpdateComment() {
        CommentUpdateDto dto = new CommentUpdateDto();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(mapper.toResponse(comment)).thenReturn(responseDto);

        CommentResponseDto result = service.update(commentId, dto);

        Assertions.assertThat(result).isEqualTo(responseDto);
        verify(mapper).updateEntity(comment, dto);
    }

    @Test
    void deleteShouldCallRepository() {
        service.delete(commentId);
        verify(commentRepository).deleteById(commentId);
    }

    @Test
    void getAllShouldReturnList() {
        Comment entity = new Comment();
        CommentResponseDto dto = new CommentResponseDto();

        when(commentRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(dto);

        Assertions.assertThat(service.getAll()).hasSize(1);
    }

    @Test
    void getByArticleShouldReturnList() {
        Comment entity = new Comment();
        CommentResponseDto dto = new CommentResponseDto();

        when(commentRepository.findByArticleId(articleId)).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(dto);

        Assertions.assertThat(service.getByArticle(articleId)).hasSize(1);
    }
}