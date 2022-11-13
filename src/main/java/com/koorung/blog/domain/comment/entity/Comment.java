package com.koorung.blog.domain.comment.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    @Lob
    private String contents;

    private Integer likeCount;

    private Boolean isPublic;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(String contents, String password) {
        this.contents = contents;
        this.likeCount = 0;
        this.isPublic = true;
        this.password = password;
    }

    public void configMember(Member member) {
        this.member = member;
    }

    public void configPost(Post post) {
        this.post = post;
    }

    /**
     * 좋아요 수를 1 증가시키는 메소드
     */
    public void plusLikeCount() {
        this.likeCount += 1;
    }

    /**
     * 좋아요 수를 1 감소시키는 메소드 <br/>
     * (0 미만일 경우 예외 발생)
     */
    public void minusLikeCount() {
        if(likeCount == 0) throw new IllegalArgumentException("좋아요 수는 0보다 작을 수 없습니다");
        this.likeCount -= 1;
    }

    /**
     * 댓글의 공개여부를 설정하는 메소드
     * @param isPublic
     */
    public void updateIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
