package com.koorung.blog.dto;

import com.koorung.blog.domain.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
public class PostResponseDto {
    private final String title;
    private final String contents;
    private final String createdDate;
    private final String lastModifiedDate;


    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        // LocalDateTime -> Localize된 String으로 변환... (매우 유용할듯?)
        this.createdDate = post.getCreatedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        this.lastModifiedDate = post.getLastModifiedDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
