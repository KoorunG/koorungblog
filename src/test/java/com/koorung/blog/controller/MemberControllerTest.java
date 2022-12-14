package com.koorung.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberLoginDto;
import com.koorung.blog.domain.member.entity.Address;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    private MockHttpSession mockHttpSession;

    @BeforeEach
    void init() {
        mockHttpSession = new MockHttpSession();
    }

    @BeforeEach
    public void tearup() {
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

    @Test
    @DisplayName("???????????? ?????? ?????????")
    void join_success() throws Exception {
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung")
                .name("??????")
                .password("test1234!").build())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ??? ??????????????? ????????? ???????????? ????????? ?????? ??????")
    void join_fail_invalid_password() throws Exception {
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung")
                .name("??????")
                .password("test123").build())
                // TODO) ???????????? ?????? ?????? ????????? ??????_221105
                .andExpect(jsonPath("$.message").value("3?????? ?????? ??????"))    // NORMAL
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ??? ????????? ???????????? ????????? ?????? ??????")
    void join_fail_not_exist_username() throws Exception {
        assertJoinMember(MemberCreateDto
                .builder()
                .id("testId")
                .password("test1234!@").build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.name").value("????????? ????????? ???????????? ?????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ???????????? ????????? ????????? ???????")
    void join_fail_not_exist_all() throws Exception {
        //given
        assertJoinMember(MemberCreateDto.builder().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.password").value("??????????????? ????????? ???????????? ?????????."))
                .andExpect(jsonPath("$.validation.name").value("????????? ????????? ???????????? ?????????."))
                .andExpect(jsonPath("$.validation.id").value("ID??? ????????? ???????????? ?????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ??? ????????? ??????")
    void join_success_exist_all() throws Exception {
        //given
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung1234")
                .password("test1234!@")
                .name("??????")
                .email("koorung23@test.com")
                .city("?????????")
                .street("?????????")
                .zipCode("12345")
                .build())
                .andExpect(status().isOk())
                // ???????????? ??? ???????????? ?????????...
//                .andExpect(jsonPath("$.id").value("koorung1234"))
//                .andExpect(jsonPath("$.password").value("test1234!@"))
//                .andExpect(jsonPath("$.email").value("koorung23@test.com"))
//                .andExpect(jsonPath("$.role").value("USER"))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    void find_all_member() throws Exception {
        //given
        List<Member> memberList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Member.builder()
                        .loginId("test" + i)
                        .password("test!@#$" + i)
                        .username("?????????" + i)
                        .address(Address.builder()
                                .city("?????????" + i)
                                .zipCode("1234" + i).build())
                        .build())
                .collect(Collectors.toList());
        memberRepository.saveAll(memberList);

        //when
        mockMvc.perform(get("/members")
                        .contentType(APPLICATION_JSON)
                        .characterEncoding(UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andDo(print());
    }

    @Test
    @DisplayName("DB??? ????????? ?????? ?????????, ??????????????? ????????? ?????? ??? ????????? ??????")
    void login_fail() throws Exception {

        //given - ???????????? ??????
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());
        //expected - DB??? ????????? ????????? ?????? ?????? ????????? 404 ??????????????? ????????? ????????? ????????? ??????
        assertLoginMember(MemberLoginDto.builder()
                .loginId("koorung123")
                .password("test1234!@")
                .build())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message").value("???????????? ?????? ??????????????????."))
                .andDo(print());

    }

    @Test
    @DisplayName("????????? ??????")
    void login_success() throws Exception {
        //given - ???????????? ??????
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());

        //expected
        assertLoginMember(MemberLoginDto.builder()
                .loginId("koorung")
                .password("test1234!@").build())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ??????????????? ???????????? ????????? ??? Exception ??????")
    void login_fail_exception() throws Exception {
        //given - ???????????? ??????
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());

        //expected
        assertLoginMember(MemberLoginDto.builder().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.message").value("?????? ?????? ??????"))
                .andExpect(jsonPath("$.validation.loginId").value("???????????? ????????? ???????????? ?????????."))
                .andExpect(jsonPath("$.validation.password").value("??????????????? ????????? ???????????? ?????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void session_test() {
        //given
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());

        assertThat(mockHttpSession).isNotNull();
    }

    @Test
    @DisplayName("????????? ?????? ??? ????????? ????????????")
    void login_success_get_session() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());

        //when
        assertLoginMember(MemberLoginDto.builder()
                .loginId("koorung")
                .password("test1234!@")
                .build())
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(mockHttpSession.getAttribute("loginId")).isEqualTo("koorung");
    }

    @Test
    @DisplayName("???????????? ?????????")
    void logout() throws Exception {
        //given

        // 1.????????????
        memberRepository.save(Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.GUEST)
                .username("??????").build());

        // 2.?????????
        assertLoginMember(MemberLoginDto.builder()
                .loginId("koorung")
                .password("test1234!@")
                .build())
                .andExpect(status().isOk())
                .andDo(print());

        mockHttpSession.getAttributeNames().asIterator().forEachRemaining((each) -> {
            System.out.println(each + " ::: " + mockHttpSession.getAttribute(each));
        });

        // 3.??????????????? ???????
        mockMvc.perform(get("/logout").session(mockHttpSession))
                .andDo(print());

//        mockHttpSession.getAttributeNames().asIterator().forEachRemaining((each) -> {
//            System.out.println(each + " ::: " + mockHttpSession.getAttribute(each));
//        });

        // ???????????? -> session.invalidate()
        assertThat(mockHttpSession.isInvalid()).isTrue();
    }

    private String createLoginRequest(MemberLoginDto memberLoginDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(memberLoginDto);
    }

    private String createMemberRequest(MemberCreateDto memberCreateDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(memberCreateDto);
    }

    private ResultActions assertJoinMember(MemberCreateDto memberCreateDto) throws Exception {
        return mockMvc.perform(post("/members")
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(createMemberRequest(memberCreateDto)));
    }

    private ResultActions assertLoginMember(MemberLoginDto memberLoginDto) throws Exception {
        return mockMvc.perform(post("/login").session(mockHttpSession)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(createLoginRequest(memberLoginDto)));
    }

}