package com.koorung.blog.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateDto {
    private String title;
    private String contents;

    @Builder
    public PostUpdateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
