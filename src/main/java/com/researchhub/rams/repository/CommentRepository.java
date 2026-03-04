package com.researchhub.rams.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.researchhub.rams.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByArticleId(UUID articleId);
}