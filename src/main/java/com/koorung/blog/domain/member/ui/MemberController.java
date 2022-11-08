package com.koorung.blog.domain.member.ui;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberLoginDto;
import com.koorung.blog.domain.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/members/login")
    public void loginMember(@RequestBody @Valid MemberLoginDto memberLoginDto) {
        memberService.login(memberLoginDto);
    }
}
