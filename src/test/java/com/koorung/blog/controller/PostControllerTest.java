package com.koorung.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.domain.post.application.PostService;
import com.koorung.blog.domain.post.dto.PostCreateDto;
import com.koorung.blog.domain.post.dto.PostUpdateDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.exception.PostNotExistException;
import com.koorung.blog.domain.post.repository.PostRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @BeforeEach
    void tearup() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("?????? ????????? ??? ???????????? ????????? ????????? ?????? ??????")
    void postNoTitle() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(createPostWithMember("", "", createDefaultMember())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.size()").isNumber())
                .andExpect(jsonPath("$.validation.title").value("????????? ????????? ???????????? ?????????."))
                .andExpect(jsonPath("$.validation.contents").value("????????? ????????? ???????????? ?????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("??? ?????? ??????")
    void postSuccess() throws Exception {
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(createPostWithMember("?????????", "?????????", createDefaultMember())))
                .andExpect(status().isOk())
//                .andExpect(content().string("1")) // ?????? ?????? ??? ????????? ?????? (1)
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? ??? ?????? ??? ?????? ??????")
    void getOneFail() throws Exception {
        //given
        mockMvc.perform(get("/posts/{postId}", 100000))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("?????? ???????????? ????????????!"))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ??? ??????")
    void getAll() throws Exception {
        //given
        List<Post> postList = IntStream.rangeClosed(1, 5).mapToObj(i -> Post.builder().title("??????" + i).contents("??????" + i).build()).collect(Collectors.toList());
        postRepository.saveAll(postList);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$.[0].title").value("??????1"))
                .andExpect(jsonPath("$.[0].contents").value("??????1"))
                .andDo(print());
    }

    @Test
    @DisplayName("??? ????????????")
    void deletePost() throws Exception {
        //given
        List<Post> postList = IntStream.rangeClosed(1, 5).mapToObj(i -> Post.builder().title("??????" + i).contents("??????" + i).build()).collect(Collectors.toList());
        postRepository.saveAll(postList);

        List<Long> idList = postList.stream().map(Post::getId).collect(Collectors.toList());

        // 0 ~ 4
        int i = (int) (Math.random() * 5);
        Long randomId = idList.get(i);

        // expected
        mockMvc.perform(delete("/posts/{postId}", randomId)
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8))
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(PostNotExistException.class, () -> postService.getPostById(randomId));
    }

    @Test
    @DisplayName("???????????? ?????? ??? ?????? ?????? ??? ????????????")
    void deletePostFail() throws Exception {
        // expected
        mockMvc.perform(delete("/posts/{postId}", 1000L)
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @DisplayName("??? ???????????? (Patch??? ??????)")
    void modifyPost() throws Exception {

        //given
        List<PostCreateDto> postList = IntStream.rangeClosed(11, 15).mapToObj(i -> PostCreateDto.builder()
                .memberId(createDefaultMember().getId())
                .title("??????" + i)
                .contents("??????" + i)
                .build()).collect(Collectors.toList());

        List<Long> idList = postList.stream().map(dto -> postService.savePost(dto)).collect(Collectors.toList());

        // 0 ~ 4
        int i = (int) (Math.random() * 5);
        Long randomId = idList.get(i);

        // ??? ?????? ??? 3????????? ??????
        Thread.sleep(3000);

        // expected
        mockMvc.perform(patch("/posts/{postId}", randomId)
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(objectMapper.writeValueAsString(PostUpdateDto.builder().title("??????????????????").contents("??????????????????").build()))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("??????????????????"))
                .andExpect(jsonPath("$.contents").value("??????????????????"))
                .andDo(print());
    }


    @Test
    @DisplayName("???????????? ?????? ??? ?????? ?????? ??? ????????????")
    void updatePostFail() throws Exception {
        // expected
        mockMvc.perform(patch("/posts/{postId}", 1000L)
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(objectMapper.writeValueAsString(PostUpdateDto.builder().title("??????????????????").contents("??????????????????").build())))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ????????? ??? ??????????????? ?????? ??????????????? ?????????")
    void post_with_member() {
        // expected
        IntStream.rangeClosed(1, 5).forEach(i -> {
            try {
                mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8)
                        .content(createPostWithMember("??????" + i, "??????" + i, createDefaultMember())))
                        .andExpect(status().isOk())
                        .andDo(print());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Member createDefaultMember() {
        return memberRepository.save(Member.builder()
                .loginId("test")
                .password("test1234!@")
                .username("???????????????")
                .role(Role.ADMIN)
                .build());
    }

    // JSON.stringify()
//    private String createPost(String title, String contents) throws JsonProcessingException {
//
//        Member mockMember = Mockito.mock(Member.class);
//        PostCreateDto createDto = PostCreateDto.builder()
//                .memberId(mockMember.getId())
//                .title(title)
//                .contents(contents)
//                .build();
//        return objectMapper.writeValueAsString(createDto);
//    }


    private String createPostWithMember(String title, String contents, Member member) throws JsonProcessingException {
        PostCreateDto createDto = PostCreateDto.builder()
                .memberId(member.getId())
                .title(title)
                .contents(contents)
                .build();
        return objectMapper.writeValueAsString(createDto);
    }
}