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
        return mapper.toResponse(
                articleRepository.findById(id).orElseThrow()
        );
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getByTitle(String title) {
        return articleRepository.findByTitle(title).stream().map(mapper::toResponse).toList();
    }

    public ArticleResponseDto create(ArticleRequestDto dto) {

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow();

        Article article = mapper.toEntity(dto);
        article.setAuthor(author);
        article.setStatus(ArticleStatus.DRAFT);

        return mapper.toResponse(articleRepository.save(article));
    }

    public ArticleResponseDto update(UUID id, ArticleUpdateDto dto) {

        Article article = articleRepository.findById(id).orElseThrow();

        mapper.updateEntity(article, dto);

        return mapper.toResponse(articleRepository.save(article));
    }

    public void delete(UUID id) {
        articleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAllWithNPlusOne() {

        List<Article> articles = articleRepository.findAll();

        for (Article article : articles) {
            article.getComments().size();
        }

        return articles.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAllOptimized() {

        List<Article> articles = articleRepository.findAllWithRelations();

        return articles.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void createArticleWithRelationsNoTransaction(ArticleRequestDto dto) {

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow();

        Article article = mapper.toEntity(dto);
        article.setAuthor(author);
        article.setStatus(ArticleStatus.DRAFT);

        articleRepository.save(article);

        Comment comment = new Comment();
        comment.setText("First comment");
        comment.setArticle(article);
        comment.setAuthor(author);

        commentRepository.save(comment);

        if (true) {
            throw new TransactionSimulationException("Simulated failure");
        }
    }

    @Transactional
    public void createArticleWithRelationsTransactional(ArticleRequestDto dto) {

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow();

        Article article = mapper.toEntity(dto);
        article.setAuthor(author);
        article.setStatus(ArticleStatus.DRAFT);

        articleRepository.save(article);

        Comment comment = new Comment();
        comment.setText("Transactional comment");
        comment.setArticle(article);
        comment.setAuthor(author);

        commentRepository.save(comment);

        if (true) {
            throw new TransactionSimulationException("Simulated failure");
        }
    }
}