package com.koorung.blog.repository;

import com.koorung.blog.domain.Member;

import java.util.List;

public interface MemberQueryRepository {
    List<Member> findMember(String loginId, String password);
}
