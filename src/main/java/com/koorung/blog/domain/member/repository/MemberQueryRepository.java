package com.koorung.blog.domain.member.repository;

import com.koorung.blog.domain.member.entity.Member;

import java.util.List;

public interface MemberQueryRepository {
    List<Member> findMemberByLoginId(String loginId);
    List<Member> findMemberByCorrectLoginInfo(String loginId, String password);
}
