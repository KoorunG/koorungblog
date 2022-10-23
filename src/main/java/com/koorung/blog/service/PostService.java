package com.koorung.blog.service;

import com.koorung.blog.domain.Post;
import com.koorung.blog.exception.PostNotExistException;
import com.koorung.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 글을 등록하고 식별자를 리턴
    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotExistException::new);
    }

    public List<Post> getPostAll() {
        return postRepository.findAll();
    }
}
