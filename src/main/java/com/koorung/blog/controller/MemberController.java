package com.koorung.blog.controller;

import com.koorung.blog.domain.Member;
import com.koorung.blog.dto.MemberCreateDto;
import com.koorung.blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public void joinMember(@RequestBody MemberCreateDto memberCreateDto){
        memberService.join(Member.builder()
                .username(memberCreateDto.getName())
                .loginId(memberCreateDto.getId())
                .password(memberCreateDto.getPassword())
                .build());
    }
}
