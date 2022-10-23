package com.koorung.blog.service;

import com.koorung.blog.domain.Member;
import com.koorung.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member join(Member member) {
        // 1. 패스워드 검증
        member.checkPassword(member.getPassword());
        // 2. 저장
        return memberRepository.save(member);
    }
}
