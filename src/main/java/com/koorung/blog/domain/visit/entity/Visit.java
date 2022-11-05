package com.koorung.blog.domain.visit.entity;


import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "visit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String contents;

    @Builder
    public Visit(Member member, String contents) {
        this.member = member;
        this.contents = contents;
    }
}
