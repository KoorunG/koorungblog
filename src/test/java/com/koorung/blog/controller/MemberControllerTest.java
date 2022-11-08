package com.koorung.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.entity.Address;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MemberRepository repository;

    @AfterEach
    public void tearup() {
        repository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void join_success() throws Exception {
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung")
                .name("쿠렁")
                .password("test1234!").build())
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 시 비밀번호가 조건을 만족하지 않으면 예외 발생")
    void join_fail_invalid_password() throws Exception {
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung")
                .name("쿠렁")
                .password("test123").build())
                // TODO) 비밀번호 체크 조건 메세지 수정_221105
                .andExpect(jsonPath("$.message").value("3가지 조건 충족"))    // NORMAL
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 시 이름을 입력하지 않으면 예외 발생")
    void join_fail_not_exist_username() throws Exception {
        assertJoinMember(MemberCreateDto
                .builder()
                .id("testId")
                .password("test1234!@").build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.name").value("이름은 반드시 입력해야 합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("모두 입력하지 않으면 어떻게 될까?")
    void join_fail_not_exist_all() throws Exception {
        //given
        assertJoinMember(MemberCreateDto.builder().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.password").value("비밀번호는 반드시 입력해야 합니다."))
                .andExpect(jsonPath("$.validation.name").value("이름은 반드시 입력해야 합니다."))
                .andExpect(jsonPath("$.validation.id").value("ID는 반드시 입력해야 합니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("모든 값을 입력했을 때 리턴값 체크")
    void join_success_exist_all() throws Exception {
        //given
        assertJoinMember(MemberCreateDto.builder()
                .id("koorung1234")
                .password("test1234!@")
                .name("쿠렁")
                .email("koorung23@test.com")
                .city("남양주")
                .street("진관로")
                .zipCode("12345")
                .build())
                .andExpect(status().isOk())
                // 회원가입 시 식별자를 리턴함...
//                .andExpect(jsonPath("$.id").value("koorung1234"))
//                .andExpect(jsonPath("$.password").value("test1234!@"))
//                .andExpect(jsonPath("$.email").value("koorung23@test.com"))
//                .andExpect(jsonPath("$.role").value("USER"))
                .andDo(print());
    }

    @Test
    @DisplayName("모든 회원 조회")
    void find_all_member() throws Exception {
        //given
        List<Member> memberList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Member.builder()
                        .loginId("test" + i)
                        .password("test!@#$" + i)
                        .username("테스트" + i)
                        .address(Address.builder()
                                .city("남양주" + i)
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
    @DisplayName("회원의 정보 수정")
    void patch_member_info() {
        //given

        //when

        //then

    }

    private String createMember(MemberCreateDto memberCreateDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(memberCreateDto);
    }

    private ResultActions assertJoinMember(MemberCreateDto memberCreateDto) throws Exception {
        return mockMvc.perform(post("/members")
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8)
                .content(createMember(memberCreateDto)));
    }

}