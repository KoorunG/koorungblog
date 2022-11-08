package com.koorung.blog.domain.tag.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.relation.TagPost;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private List<TagPost> tagPosts = new ArrayList<>();

    @Builder
    public Tag(String name, List<TagPost> tagPosts) {
        this.name = name;
        this.tagPosts = tagPosts;
    }


    public void configPost(TagPost tagPost) {
        this.tagPosts.add(tagPost);
        tagPost.updateTag(this);
    }

    // 태그된 게시글의 숫자를 리턴
    public Integer getTagged() {
        return tagPosts.size();
    }
}

