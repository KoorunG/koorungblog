package com.koorung.blog.domain.post.repository;

import com.koorung.blog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
