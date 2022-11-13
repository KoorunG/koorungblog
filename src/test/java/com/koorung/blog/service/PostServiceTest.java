package com.koorung.blog.service;

import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.exception.MemberNotExistException;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.domain.post.application.PostService;
import com.koorung.blog.domain.post.dto.PostCreateDto;
import com.koorung.blog.domain.post.dto.PostUpdateDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.exception.PostNotExistException;
import com.koorung.blog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class PostServiceTest {

    @BeforeEach
    void init() {
        postRepository.deleteAll();
    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 하나 저장하기")
    void postOne() {
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .memberId(createDefaultMember().getId())
                .title("글제목")
                .contents("글내용")
                .build();

        //when
        Long id = postService.savePost(postCreateDto);
        Post saved = postRepository.findById(id).orElseThrow(PostNotExistException::new);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved).extracting("title").isEqualTo("글제목");
        assertThat(saved).extracting("contents").isEqualTo("글내용");
    }

    @Test
    @DisplayName("글 하나 조회하기")
    void getOne() {
        //given
        Post post = Post.builder()
                .title("글제목")
                .contents("글내용")
                .build();
        postRepository.save(post);

        //when
        Post findPost = postService.getPostById(post.getId());

        //then
        assertThat(findPost).isNotNull();
        assertThat(findPost).extracting("title").isEqualTo("글제목");
        assertThat(findPost).extracting("contents").isEqualTo("글내용");
    }

    @Test
    @DisplayName("글 여러개 조회하기")
    void getAll() {
        //given
        List<Post> postList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .contents("내용" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        //when
        List<Post> postAll = postService.getPostAll();

        //then
        assertThat(postAll.size()).isEqualTo(5);
        assertThat(postAll).extracting("title").containsExactly("제목1", "제목2", "제목3", "제목4", "제목5");
        assertThat(postAll).extracting("contents").containsExactly("내용1", "내용2", "내용3", "내용4", "내용5");
    }

    @Test
    @DisplayName("글 삭제하기")
    void delete_post() {
        //given
        Post post = Post.builder()
                .title("글제목")
                .contents("글내용")
                .build();
        postRepository.save(post);

        //when
        postService.deletePost(post.getId());

        //then
        assertThrows(PostNotExistException.class, () -> postService.getPostById(post.getId()));
    }

    @Test
    @DisplayName("글 수정하기")
    void update_post() {
        //given
        Post post = Post.builder()
                .title("글제목")
                .contents("글내용")
                .build();
        postRepository.save(post);

        //when
        Post updatePost = postService.updatePost(post.getId(), PostUpdateDto.builder().title("글수정테스트").contents("글수정테스트").build());

        //then
        assertThat(updatePost).extracting("title").isEqualTo("글수정테스트");
        assertThat(updatePost).extracting("contents").isEqualTo("글수정테스트");
    }


    @Test
    @DisplayName("유저가 지정된 상태로 글쓰기 테스트")
    void post_with_member() {
        //given

        // 테스트용 멤버 저장
        Member member = memberRepository.save(Member.builder()
                .loginId("koorung23")
                .password("test1234!@")
                .username("쿠렁")
                .role(Role.ADMIN).build());

        // 멤버 검색(로그인)
        Member loginMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotExistException::new);

        //when
        Long saveId = postService.savePost(PostCreateDto.builder()
                .memberId(loginMember.getId())
                .title("글제목@@@@")
                .contents("글내용@@@@")
                .build());

        Post findPost = postService.getPostById(saveId);

        //then
        // 왜 1+n 이 걸리는거지? -> cascade나 orphanRemoval 옵션을 키면 그 엔티티도 참조하므로!
        // @Builder를 사용할 경우 다른 엔티티를 참조하는 필드를 파라미터로 넘길 때 주의해야한다!
        assertThat(findPost)
                .isNotNull()
                .extracting("member")
                .extracting("loginId")
                .isEqualTo("koorung23");
    }

    private Member createDefaultMember() {
        return memberRepository.save(Member.builder()
                .loginId("test")
                .password("test1234!@")
                .username("디폴트멤버")
                .role(Role.ADMIN)
                .build());
    }
}