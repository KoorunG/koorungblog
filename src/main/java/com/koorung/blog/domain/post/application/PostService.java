package com.koorung.blog.domain.post.application;

import com.koorung.blog.domain.post.dto.PostCreateDto;
import com.koorung.blog.domain.post.dto.PostUpdateDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.exception.PostNotExistException;
import com.koorung.blog.domain.post.repository.PostRepository;
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
    public Long savePost(PostCreateDto postCreateDto) {
        Post post = new Post(postCreateDto);
        return postRepository.save(post).getId();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotExistException::new);
    }

    public List<Post> getPostAll() {
        return postRepository.findAll();
    }


    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotExistException::new);
        postRepository.delete(post);
    }

    @Transactional
    public Post updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotExistException::new);
        post.modifyPost(postUpdateDto);
        return post;
    }
}
