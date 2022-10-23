package com.koorung.blog.service;

import com.koorung.blog.domain.Post;
import com.koorung.blog.exception.PostNotExistException;
import com.koorung.blog.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 하나 저장하기")
    void postOne() {
        //given
        Post post = Post.builder()
                .title("글제목")
                .contents("글내용")
                .build();

        //when
        Long id = postService.savePost(post).getId();
        Post saved = postRepository.findById(id).orElseThrow(PostNotExistException::new);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved).extracting("title").isEqualTo("글제목");
        assertThat(saved).extracting("contents").isEqualTo("글내용");
    }

    @Test
    @DisplayName("글 하나 조회하기")
    void getOne() {
        //given
        Post post = Post.builder()
                .title("글제목")
                .contents("글내용")
                .build();
        postRepository.save(post);

        //when
        Post findPost = postService.getPostById(post.getId());

        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost).extracting("title").isEqualTo("글제목");
        assertThat(findPost).extracting("contents").isEqualTo("글내용");
    }

    @Test
    @DisplayName("글 여러개 조회하기")
    void getAll() {
        //given
        List<Post> postList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .contents("내용" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        //when
        List<Post> postAll = postService.getPostAll();

        //then
        assertThat(postAll.size()).isEqualTo(5);
        assertThat(postAll).extracting("title").containsExactly("제목1", "제목2", "제목3", "제목4", "제목5");
        assertThat(postAll).extracting("contents").containsExactly("내용1", "내용2", "내용3", "내용4", "내용5");
    }
}