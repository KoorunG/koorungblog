package com.koorung.blog.domain.post.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.post.dto.PostCreateDto;
import com.koorung.blog.domain.post.dto.PostUpdateDto;
import com.koorung.blog.domain.relation.CategoryPost;
import com.koorung.blog.domain.relation.TagPost;
import com.koorung.blog.domain.tag.entity.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    @Lob
    private String contents;

    private Integer likeCount;

    private Integer viewCount;

    @OneToMany(mappedBy = "post")
    private List<CategoryPost> categoryPosts = new ArrayList<>();

//    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "post")   // cascade나 orphanRemoval 옵션을 키면 1+n 문제가 발생했다! (당연한건가?)
    private List<TagPost> tagPosts = new ArrayList<>();

    // Post -> File 1:M 단방향 관계 (글 하나에 여러 File이 존재할 수 있음)
//    @OneToMany
//    @JoinColumn(name = "file_id")
//    private List<File> files = new ArrayList<>();

    // 글은 작성자의 정보를 알아야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.likeCount = 0;
        this.viewCount = 0;
    }

    public void addTag(TagPost... tagpost){
        tagPosts.addAll(Arrays.asList(tagpost));
    }

    /**
     * 좋아요 수를 1 증가시키는 메소드
     */
    public void plusLikeCount() {
        this.likeCount += 1;
    }

    /**
     * 좋아요 수를 1 감소시키는 메소드 <br>
     * (0 미만일 경우 예외 발생)
     */
    public void minusLikeCount() {
        if(likeCount == 0) throw new IllegalArgumentException("좋아요 수는 0보다 작을 수 없습니다");
        this.likeCount -= 1;
    }

    public Post(PostCreateDto postCreateDto) {
        this.title = postCreateDto.getTitle();
        this.contents = postCreateDto.getContents();
//        this.member = postCreateDto.getMember();
    }

    /**
     * 조회수를 1 증가시키는 메소드
     */
    public void plusViewCount() {
        this.viewCount += 1;
    }

    // 연관관계 설정 메소드
    public void configMember(Member member) {
        this.member = member;
        member.getPostList().add(this);
    }

    public void configTag(TagPost tagPost) {
        this.tagPosts.add(tagPost);
        tagPost.updatePost(this);
    }

    // 글 수정 메소드
    public void updatePost(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.contents = postUpdateDto.getContents();
    }
}
