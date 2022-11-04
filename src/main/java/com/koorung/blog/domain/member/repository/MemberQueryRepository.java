package com.koorung.blog.domain.member.repository;

import com.koorung.blog.domain.member.entity.Member;

import java.util.List;

public interface MemberQueryRepository {
    List<Member> findMember(String loginId, String password);
}
