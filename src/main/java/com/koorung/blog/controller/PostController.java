package com.koorung.blog.controller;

import com.koorung.blog.domain.Post;
import com.koorung.blog.dto.PostCreateDto;
import com.koorung.blog.dto.PostResponseDto;
import com.koorung.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponseDto> getPostAll() {
        return postService.getPostAll().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return new PostResponseDto(postService.getPostById(postId));
    }

    @PostMapping("/posts")
    public Long createPost(@RequestBody @Valid PostCreateDto postCreateDto) {

        Post post = Post.builder()
                .title(postCreateDto.getTitle())
                .contents(postCreateDto.getContents())
                .build();

        // 저장 후
        return postService.savePost(post).getId();
    }
}
