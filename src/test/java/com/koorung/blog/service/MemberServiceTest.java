package com.koorung.blog.service;

import com.koorung.blog.domain.Member;
import com.koorung.blog.domain.Role;
import com.koorung.blog.dto.MemberCreateDto;
import com.koorung.blog.exception.MemberNotExistException;
import com.koorung.blog.exception.PasswordInvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입시 비밀번호가 조건에 맞지 않으면 예외 발생")
    void join_password_fail() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("koorung1234")
                .role(Role.ADMIN)
                .password("1234")
                .name("쿠렁")
                .build();

        // then
        assertThrows(PasswordInvalidException.class, () -> memberService.join(memberCreateDto));
    }

    @Test
    @DisplayName("관리자 회원가입 성공")
    void join_admin_success() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("admin1234")
                .role(Role.ADMIN)
                .password("test1234kk!")
                .name("관리자")
                .build();

        //when
        Long memberId = memberService.join(memberCreateDto);

        //then
        assertThat(memberId).isNotNull();
    }

    @Test
    @DisplayName("일반 유저 회원가입 성공")
    void join_user_success() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .role(Role.USER)
                .password("user1234@!")
                .name("일반유저")
                .build();

        //when
        Long memberId =  memberService.join(memberCreateDto);

        //then
        assertThat(memberId).isNotNull();
    }

    @Test
    @DisplayName("회원가입 하지 않은 유저의 권한은 GUEST")
    void none_join_guest() {
        //given
        Member member = new Member();
        //then
        assertThat(member).extracting("role").isEqualTo(Role.GUEST);
    }

    @Test
    @DisplayName("가입되지 않은 유저정보로 로그인할 때 예외 발생")
    void login_fail_not_exist_user() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .role(Role.USER)
                .password("user1234@!")
                .name("일반유저")
                .build();

        memberService.join(memberCreateDto);

        //when
        assertThrows(MemberNotExistException.class, () -> {
            memberService.login("adfs", "sdf");
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        //givenㄹ
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .role(Role.USER)
                .password("user1234@!")
                .name("일반유저")
                .build();

        memberService.join(memberCreateDto);

        //when
        Member loginUser = memberService.login("user1234", "user1234@!");

        //then
        assertThat(loginUser).extracting("username").isEqualTo("일반유저");
        assertThat(loginUser).extracting("loginId").isEqualTo("user1234");
        assertThat(loginUser).extracting("password").isEqualTo("user1234@!");
    }
}
