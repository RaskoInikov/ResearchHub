package com.researchhub.rams.entity;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Long id;

    private String title;

    private String abstractText;

    private Instant publicationDate;
}
