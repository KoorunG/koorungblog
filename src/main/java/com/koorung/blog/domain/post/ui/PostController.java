package com.koorung.blog.domain.post.ui;

import com.koorung.blog.domain.post.application.PostService;
import com.koorung.blog.domain.post.dto.PostCreateDto;
import com.koorung.blog.domain.post.dto.PostResponseDto;
import com.koorung.blog.domain.post.dto.PostUpdateDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.repository.PostRepository;
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
    private final PostRepository postRepository;

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
        return postService.savePost(postCreateDto);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponseDto modifyPost(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        Post post = postService.updatePost(postId, postUpdateDto);
        return new PostResponseDto(post);
    }
}
