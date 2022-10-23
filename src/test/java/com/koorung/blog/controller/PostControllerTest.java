package com.koorung.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koorung.blog.domain.Post;
import com.koorung.blog.dto.PostCreateDto;
import com.koorung.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @BeforeEach
    void init() {
        postRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글을 저장할 때 제목이나 내용이 없으면 예외 발생")
    void postNoTitle() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(createPost("", "")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.size()").isNumber())
                .andExpect(jsonPath("$.validation.title").value("제목은 반드시 입력해야 합니다."))
                .andExpect(jsonPath("$.validation.contents").value("내용은 반드시 입력해야 합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("글 정상 저장")
    void postSuccess() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(createPost("글제목", "글내용")))
                .andExpect(status().isOk())
//                .andExpect(content().string("1")) // 단건 저장 후 식별자 리턴 (1)
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 글 조회 시 에러 발생")
    void getOneFail() throws Exception {
        //given
        mockMvc.perform(get("/posts/{postId}", 100000))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("글이 존재하지 않습니다!"))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 글 조회")
    void getAll() throws Exception {
        //given
        List<Post> postList = IntStream.rangeClosed(1, 5).mapToObj(i -> Post.builder().title("제목" + i).contents("내용" + i).build()).collect(Collectors.toList());
        postRepository.saveAll(postList);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$.[0].title").value("제목1"))
                .andExpect(jsonPath("$.[0].contents").value("내용1"))
                .andDo(print());
    }

    // JSON.stringify()
    private String createPost(String title, String contents) throws JsonProcessingException {
        PostCreateDto createDto = PostCreateDto.builder()
                .title(title)
                .contents(contents)
                .build();
        return objectMapper.writeValueAsString(createDto);
    }
}