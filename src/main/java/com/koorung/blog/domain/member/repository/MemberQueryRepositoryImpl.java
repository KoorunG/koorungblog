package com.koorung.blog.domain.member.repository;

import com.koorung.blog.domain.member.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.koorung.blog.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    // querydsl용 JPAQueryFactory 주입
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findMember(String loginId, String password) {
        return queryFactory
                .selectFrom(member)
                .where(loginIdEq(loginId), passwordEq(password))
                .fetch();
    }

    private BooleanExpression loginIdEq(String loginIdCondi) {
        return loginIdCondi == null ? null : member.loginId.eq(loginIdCondi);
    }

    private BooleanExpression passwordEq(String passwordCondi) {
        return passwordCondi == null ? null : member.password.eq(passwordCondi);
    }
}
