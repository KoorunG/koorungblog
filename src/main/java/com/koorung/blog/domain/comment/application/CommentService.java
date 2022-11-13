package com.koorung.blog.domain.comment.application;

import com.koorung.blog.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PackageName : com.koorung.blog.domain.comment.application
 * FileName : CommentService
 * Author : Koorung
 * Date : 2022년 11월 13일
 * Description : Comment 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


}
