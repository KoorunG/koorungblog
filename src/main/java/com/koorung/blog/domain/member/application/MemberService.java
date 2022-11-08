package com.koorung.blog.domain.member.application;

import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberUpdateDto;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.exception.AlreadyExistMemberException;
import com.koorung.blog.domain.member.exception.MemberNotExistException;
import com.koorung.blog.domain.member.exception.PasswordInvalidException;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.global.utils.pwchecker.PasswordStatus;
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
                .role(Role.USER)
                .email(memberCreateDto.getEmail())
                .address(memberCreateDto.getAddress())
                .build();

        // 1. 같은 아이디를 가지고 있는 요청을 보내면 예외 발생
        List<Member> memberByLoginId = memberRepository.findMemberByLoginId(member.getLoginId());
        if (!memberByLoginId.isEmpty()) throw new AlreadyExistMemberException();

        // 2. 패스워드 검증
        PasswordStatus passwordStatus = member.checkPassword(member.getPassword());
        if (!passwordStatus.isValid()) throw new PasswordInvalidException(passwordStatus.getMessage());

        // 3. 저장
        return memberRepository.save(member).getId();
    }

    public Member login(String loginId, String password) {

        // 로그인 진행
        List<Member> findMember = memberRepository.findMemberByCorrectLoginInfo(loginId, password);
        if (findMember.isEmpty()) throw new MemberNotExistException();
        Member member = findMember.get(0);

        // 로그인 시 최종 접근 시간 설정
        member.configLastAccessDateNow();
        return member;
    }

    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member modifyMemberInfo(Long id, MemberUpdateDto updateDto) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotExistException::new);
        member.updateMember(updateDto);
        return member;
    }
}
