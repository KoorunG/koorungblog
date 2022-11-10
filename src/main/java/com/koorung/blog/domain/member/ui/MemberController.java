package com.koorung.blog.domain.member.ui;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberLoginDto;
import com.koorung.blog.domain.member.dto.MemberResponseDto;
import com.koorung.blog.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public Long joinMember(@RequestBody @Valid MemberCreateDto memberCreateDto){
        return memberService.join(memberCreateDto);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> findAllMembers() {
        return memberService.findAllMember().stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @PostMapping("/login")
    public void loginMember(@RequestBody @Valid MemberLoginDto memberLoginDto, HttpSession session) {
        Member member = memberService.login(memberLoginDto);

        session.setAttribute("loginMember", member.getLoginId());   // 로그인아이디를 세션에 넣음 (간단한 프로젝트이므로)
        // 추후 Spring Security + JWT 적용해보자
        // 참고 : https://velog.io/@jkijki12/Spirng-Security-Jwt-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
        session.setMaxInactiveInterval(20 * 60);    // 세션 20분으로 설정
    }

    @GetMapping("/logout")
    public void logoutMember(HttpSession session) {
        session.invalidate();
    }
}
