package com.researchhub.rams.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.entity.comment.Comment;
import com.researchhub.rams.dto.comment.CommentRequestDto;
import com.researchhub.rams.dto.comment.CommentResponseDto;
import com.researchhub.rams.dto.comment.CommentUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.ArticleNotFoundException;
import com.researchhub.rams.exceptions.CommentNotFoundException;
import com.researchhub.rams.exceptions.UserNotFoundException;
import com.researchhub.rams.mapper.comment.CommentMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.CommentRepository;
import com.researchhub.rams.repository.UserRepository;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    public CommentService(
            CommentRepository commentRepository,
            ArticleRepository articleRepository,
            UserRepository userRepository,
            CommentMapper mapper) {

        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public CommentResponseDto create(CommentRequestDto dto) {

        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException(dto.getArticleId()));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(dto.getAuthorId()));

        Comment comment = mapper.toEntity(dto);
        comment.setArticle(article);
        comment.setAuthor(author);

        return mapper.toResponse(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getById(UUID id) {
        return mapper.toResponse(commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getByArticle(UUID articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CommentResponseDto update(UUID id, CommentUpdateDto dto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow();

        mapper.updateEntity(comment, dto);

        return mapper.toResponse(commentRepository.save(comment));
    }

    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }
}