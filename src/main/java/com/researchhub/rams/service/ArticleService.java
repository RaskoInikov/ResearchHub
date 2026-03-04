package com.researchhub.rams.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.researchhub.rams.dto.article.ArticleRequestDto;
import com.researchhub.rams.dto.article.ArticleResponseDto;
import com.researchhub.rams.dto.article.ArticleUpdateDto;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.article.ArticleStatus;
import com.researchhub.rams.entity.comment.Comment;
import com.researchhub.rams.entity.user.User;
import com.researchhub.rams.exceptions.TransactionSimulationException;
import com.researchhub.rams.mapper.article.ArticleMapper;
import com.researchhub.rams.repository.ArticleRepository;
import com.researchhub.rams.repository.CommentRepository;
import com.researchhub.rams.repository.UserRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ArticleMapper mapper;

    public ArticleService(
            ArticleRepository articleRepository,
            UserRepository userRepository,
            CommentRepository commentRepository,
            ArticleMapper mapper) {

        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAll() {
        return articleRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getById(UUID id) {
        return mapper.toResponse(findArticle(id));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getByTitle(String title) {
        return articleRepository.findByTitle(title)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ArticleResponseDto create(ArticleRequestDto dto) {
        User author = findUser(dto.getAuthorId());
        Article article = buildArticle(dto, author);
        return mapper.toResponse(articleRepository.save(article));
    }

    public ArticleResponseDto update(UUID id, ArticleUpdateDto dto) {
        Article article = findArticle(id);
        mapper.updateEntity(article, dto);
        return mapper.toResponse(articleRepository.save(article));
    }

    public void delete(UUID id) {
        articleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAllWithNPlusOne() {
        List<Article> articles = articleRepository.findAll();
        articles.forEach(a -> a.getComments().size());
        return articles.stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAllOptimized() {
        return articleRepository.findAllWithRelations()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void createArticleWithRelationsNoTransaction(ArticleRequestDto dto) {
        User author = findUser(dto.getAuthorId());
        Article article = articleRepository.save(buildArticle(dto, author));
        commentRepository.save(buildComment("First comment", article, author));
        throw new TransactionSimulationException("Simulated failure");
    }

    @Transactional
    public void createArticleWithRelationsTransactional(ArticleRequestDto dto) {
        User author = findUser(dto.getAuthorId());
        Article article = articleRepository.save(buildArticle(dto, author));
        commentRepository.save(buildComment("Transactional comment", article, author));
        throw new TransactionSimulationException("Simulated failure");
    }

    private Article findArticle(UUID id) {
        return articleRepository.findById(id).orElseThrow();
    }

    private User findUser(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    private Article buildArticle(ArticleRequestDto dto, User author) {
        Article article = mapper.toEntity(dto);
        article.setAuthor(author);
        article.setStatus(ArticleStatus.DRAFT);
        return article;
    }

    private Comment buildComment(String text, Article article, User author) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setArticle(article);
        comment.setAuthor(author);
        return comment;
    }
}