package com.koorung.blog.controller;

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
    public Long joinMember(@RequestBody MemberCreateDto memberCreateDto){
        return memberService.join(memberCreateDto);
    }
}
