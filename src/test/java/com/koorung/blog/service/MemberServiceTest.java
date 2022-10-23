package com.koorung.blog.service;

import com.koorung.blog.domain.Member;
import com.koorung.blog.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("관리자 회원가입 성공")
    void join() {
        //given
        Member member = Member.builder()
                .loginId("koorung1234")
                .role(Role.ADMIN)
                .password("1234")
                .username("쿠렁")
                .build();

        //when
        Member saved = memberService.join(member);

        //then
        assertThat(saved).extracting("loginId").isEqualTo("koorung1234");
        assertThat(saved).extracting("role").isEqualTo(Role.ADMIN);
        assertThat(saved).extracting("password").isEqualTo("1234");
        assertThat(saved).extracting("username").isEqualTo("쿠렁");
    }
}
