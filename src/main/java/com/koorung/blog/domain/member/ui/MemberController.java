package com.koorung.blog.domain.member.ui;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
