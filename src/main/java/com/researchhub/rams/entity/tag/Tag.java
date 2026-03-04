package com.researchhub.rams.entity.tag;

import java.util.ArrayList;
import java.util.List;

import com.researchhub.rams.entity.BaseEntity;
import com.researchhub.rams.entity.articletag.ArticleTag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag extends BaseEntity {

    // Required by JPA
    public Tag() {
        
    }
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "tag")
    private List<ArticleTag> articleTags = new ArrayList<>();

}