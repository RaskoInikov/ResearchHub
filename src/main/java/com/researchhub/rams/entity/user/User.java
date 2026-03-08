package com.researchhub.rams.entity.user;

import java.util.ArrayList;
import java.util.List;

import com.researchhub.rams.entity.BaseEntity;
import com.researchhub.rams.entity.article.Article;
import com.researchhub.rams.entity.comment.Comment;
import com.researchhub.rams.entity.review.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Review> reviews = new ArrayList<>();

    // Required by JPA
    public User() {
        // Required by JPA
    }
}