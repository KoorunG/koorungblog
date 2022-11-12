package com.koorung.blog.domain.post.application;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.exception.MemberNotExistException;
import com.koorung.blog.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    // 글을 등록하고 식별자를 리턴
    @Transactional
    public Long savePost(PostCreateDto postCreateDto) {
        Post post = new Post(postCreateDto);

        Long memberId = postCreateDto.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistException::new);

        // postCreateDto에서 member을 넘겨주지 않을 경우 연관관계 설정 메소드 호출 X
        if(member != null) post.configMember(member);
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
        post.updatePost(postUpdateDto);
        return post;
    }

    public List<Post> getPostAllWithMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistException::new);
        return postRepository.findPostWithMember(member);
    }
}
