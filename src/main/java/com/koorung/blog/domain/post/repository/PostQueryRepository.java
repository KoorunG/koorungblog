package com.koorung.blog.domain.post.repository;

import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.post.entity.Post;

import java.util.List;

/**
 * PackageName : com.koorung.blog.domain.post.repository
 * FileName : PostQueryRepository
 * Author : Koorung
 * Date : 2022년 11월 12일
 * Description : Post의 QueryDsl 확장용 인터페이스
 */
public interface PostQueryRepository {

    // 특정 멤버가 쓴 모든 글을 조회하는 메소드
    List<Post> findPostWithMember(Member member);
}
