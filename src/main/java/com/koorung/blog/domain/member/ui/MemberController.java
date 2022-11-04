package com.koorung.blog.domain.member.ui;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public Long joinMember(@RequestBody MemberCreateDto memberCreateDto){
        return memberService.join(memberCreateDto);
    }
}
