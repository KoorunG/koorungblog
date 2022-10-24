package com.koorung.blog.controller;

import com.koorung.blog.domain.Post;
import com.koorung.blog.dto.PostCreateDto;
import com.koorung.blog.dto.PostResponseDto;
import com.koorung.blog.repository.PostRepository;
import com.koorung.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostConstruct  // 테스트용으로 초기 글 5개 저장
    public void init() {
        List<Post> postList = IntStream.rangeClosed(1, 5).mapToObj(i ->
                Post.builder()
                        .title("제목 " + i)
                        .contents("내용 " + i)
                        .build()).collect(Collectors.toList());
        postRepository.saveAll(postList);
    }

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
