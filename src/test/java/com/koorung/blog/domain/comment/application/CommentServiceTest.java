package com.koorung.blog.domain.comment.application;

import com.koorung.blog.domain.comment.entity.Comment;
import com.koorung.blog.domain.comment.exception.CommentNotExistException;
import com.koorung.blog.domain.comment.repository.CommentRepository;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PackageName : com.koorung.blog.domain.comment.application
 * FileName : CommentServiceTest
 * Author : Koorung
 * Date : 2022년 11월 13일
 * Description : CommentService 테스트
 */
@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 생성하기")
    void create_comment() {

        // TODO: Mockito, BDDMockito 사용방법 익히기!
        List<Comment> savedComments = makeSituation();

        //then
        assertThat(savedComments).extracting("contents").contains("댓글달기 테스트1","댓글달기 테스트2","댓글달기 테스트3");
        assertThat(savedComments).extracting("password").contains("1234");
        // 포스트의 작성자 : 테스트
        assertThat(savedComments.get(0).getPost().getMember()).extracting("username").isEqualTo("테스트");
        // 댓글의 작성자 : 쿠렁
        assertThat(savedComments.get(0).getMember()).extracting("username").isEqualTo("쿠렁");

    }


    @Test
    @DisplayName("해당 댓글이 존재하지 않을 경우 예외 발생")
    void comment_not_exist() {
        assertThrows(CommentNotExistException.class, () -> {
            commentRepository.findById(0L).orElseThrow(CommentNotExistException::new);
        });
    }


    private List<Comment> makeSituation() {
        //given - 테스트 계정이 쓴 글 생성
        Member defaultMember = createDefaultMember();

        //when - 다른 계정이 테스트 글에 댓글달기
        Member koorung = Member.builder()
                .loginId("koorung")
                .password("test1234!@")
                .role(Role.USER)
                .username("쿠렁")
                .build();

        Post defaultPost = createDefaultPost(defaultMember);

        memberRepository.saveAll(Arrays.asList(defaultMember, koorung));
        postRepository.save(defaultPost);

        Comment comment1 = Comment.builder()
                .contents("댓글달기 테스트1")
                .password("1234")
                .build();

        Comment comment2 = Comment.builder()
                .contents("댓글달기 테스트2")
                .password("1234")
                .build();

        Comment comment3 = Comment.builder()
                .contents("댓글달기 테스트3")
                .password("1234")
                .build();

        List<Comment> commentList = Arrays.asList(comment1, comment2, comment3);

        commentList.forEach(c -> {
            c.configMember(koorung);      // koorung이
            c.configPost(defaultPost);    // test가 쓴 게시글에 댓글달기
        });

        return commentRepository.saveAll(commentList);
    }

    private Member createDefaultMember() {
        return Member.builder()
                .username("테스트")
                .loginId("testtt")
                .password("test1234!@")
                .role(Role.ADMIN)
                .build();
    }

    private Post createDefaultPost(Member member) {
        Post post = Post.builder()
                .title("제목테스트")
                .contents("내용테스트")
                .build();

        post.configMember(member);
        return post;
    }
}