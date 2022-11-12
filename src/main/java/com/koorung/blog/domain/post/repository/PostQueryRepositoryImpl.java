package com.koorung.blog.domain.post.repository;

import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koorung.blog.domain.post.entity.QPost.post;

/**
 * PackageName : com.koorung.blog.domain.post.repository
 * FileName : PostQueryRepositoryImpl
 * Author : Koorung
 * Date : 2022년 11월 12일
 * Description : PostQueryRepository의 구현체
 */
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Post> findPostWithMember(Member member) {
        return query
                .selectFrom(post)
                .where(post.member.eq(member))
                .fetch();
    }
}

