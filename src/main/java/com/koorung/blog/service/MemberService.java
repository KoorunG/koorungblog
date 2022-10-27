package com.koorung.blog.service;

import com.koorung.blog.domain.Member;
import com.koorung.blog.dto.MemberCreateDto;
import com.koorung.blog.exception.MemberNotExistException;
import com.koorung.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberCreateDto memberCreateDto) {

        Member member = Member.builder()
                .username(memberCreateDto.getName())
                .loginId(memberCreateDto.getId())
                .password(memberCreateDto.getPassword())
                .build();

        // 1. 패스워드 검증
        member.checkPassword(member.getPassword());

        // 2. 저장
        return memberRepository.save(member).getId();
    }

    public Member login(String loginId, String password) {
        List<Member> findMembers = memberRepository.findMember(loginId, password);
        if(findMembers.isEmpty()) throw new MemberNotExistException();
        return findMembers.get(0);
    }
}
