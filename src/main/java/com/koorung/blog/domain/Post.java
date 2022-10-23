package com.koorung.blog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String contents;

    // 글은 작성자의 정보를 알아야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    // 연관관계 설정 메소드
    public void configMember(Member member) {
        member.getPostList().add(this);
        this.member = member;
    }
}