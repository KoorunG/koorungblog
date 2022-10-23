package com.koorung.blog.service;

import com.koorung.blog.domain.Member;
import com.koorung.blog.domain.Role;
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
        Member member = Member.builder()
                .loginId("koorung1234")
                .role(Role.ADMIN)
                .password("1234")
                .username("쿠렁")
                .build();

        // then
        assertThrows(PasswordInvalidException.class, () -> memberService.join(member));
    }

    @Test
    @DisplayName("관리자 회원가입 성공")
    void join_admin_success() {
        //given
        Member member = Member.builder()
                .loginId("admin1234")
                .role(Role.ADMIN)
                .password("test1234kk!")
                .username("관리자")
                .build();

        //when
        Member saved = memberService.join(member);

        //then
        assertThat(saved).extracting("loginId").isEqualTo("admin1234");
        assertThat(saved).extracting("role").isEqualTo(Role.ADMIN);
        assertThat(saved).extracting("password").isEqualTo("test1234kk!");
        assertThat(saved).extracting("username").isEqualTo("관리자");
    }

    @Test
    @DisplayName("일반 유저 회원가입 성공")
    void join_user_success() {
        //given
        Member member = Member.builder()
                .loginId("user1234")
                .role(Role.USER)
                .password("user1234@!")
                .username("일반유저")
                .build();

        //when
        Member saved = memberService.join(member);

        //then
        assertThat(saved).extracting("loginId").isEqualTo("user1234");
        assertThat(saved).extracting("role").isEqualTo(Role.USER);
        assertThat(saved).extracting("password").isEqualTo("user1234@!");
        assertThat(saved).extracting("username").isEqualTo("일반유저");
    }

    @Test
    @DisplayName("회원가입 하지 않은 유저의 권한은 GUEST")
    void none_join_guest() {
        //given
        Member member = new Member();
        //then
        assertThat(member).extracting("role").isEqualTo(Role.GUEST);
    }
}
