package com.koorung.blog.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

// 글 등록용 DTO
@Getter
public class PostCreateDto {

    @NotBlank(message = "제목은 반드시 입력해야 합니다.")
    private final String title;

    @NotBlank(message = "내용은 반드시 입력해야 합니다.")
    private final String contents;

    @Builder
    public PostCreateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
