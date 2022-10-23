package com.koorung.blog.dto;

import com.koorung.blog.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final String title;
    private final String contents;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
    }
}
