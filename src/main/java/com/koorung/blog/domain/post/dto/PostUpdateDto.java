package com.koorung.blog.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {
    private final String title;
    private final String contents;

    @Builder
    public PostUpdateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
