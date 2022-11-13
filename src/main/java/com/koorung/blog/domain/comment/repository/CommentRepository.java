package com.koorung.blog.domain.comment.repository;

import com.koorung.blog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PackageName : com.koorung.blog.domain.comment.repository
 * FileName : CommentRepository
 * Author : Koorung
 * Date : 2022년 11월 13일
 * Description : Comment의 Repository 인터페이스, JPARepository 상속
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
