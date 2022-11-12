package com.koorung.blog.domain.member.ui;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.LoginResponseDto;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberLoginDto;
import com.koorung.blog.domain.member.dto.MemberResponseDto;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.post.application.PostService;
import com.koorung.blog.domain.post.dto.PostResponseDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

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
    public LoginResponseDto loginMember(@RequestBody @Valid MemberLoginDto memberLoginDto, HttpSession session) {
        Member member = memberService.login(memberLoginDto);

        // 세션에 로그인아이디와 유저명 삽입
        session.setAttribute("loginId", member.getLoginId());

        /* TODO) 추후 Spring Security + JWT 적용해보자
         *  참고 : https://velog.io/@jkijki12/Spirng-Security-Jwt-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
         */

        // 세션 20분으로 설정
//        session.setMaxInactiveInterval(20 * 60);
        return new LoginResponseDto(member);
    }

    @GetMapping("/logout")
    public void logoutMember(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/members/{memberId}/posts")
    public Result<PostResponseDto> findPostsWithMember(@PathVariable Long memberId) {
        List<Post> posts = postService.getPostAllWithMember(memberId);
        List<PostResponseDto> postResponseDtos = posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
        Result<PostResponseDto> result = new Result<>();
        result.addData(postResponseDtos);
        return result;
    }
}
