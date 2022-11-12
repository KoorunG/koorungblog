package com.koorung.blog.domain.post.dto;

import com.koorung.blog.domain.post.entity.Post;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String createdDate;
    private final String lastModifiedDate;
    private final String author;


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getMember() != null ? post.getMember().getUsername() : null;
        // LocalDateTime -> Localize된 String으로 변환... (매우 유용할듯?)
        this.createdDate = post.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        this.lastModifiedDate = post.getLastModifiedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
