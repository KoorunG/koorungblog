package com.koorung.blog.domain.member.repository;

import com.koorung.blog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository{
}
