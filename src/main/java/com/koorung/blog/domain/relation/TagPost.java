package com.koorung.blog.domain.relation;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.tag.entity.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagPost extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "tag_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;


    public void updateTag(Tag tag) {
        this.tag = getTag();
    }

    public void updatePost(Post post) {
        this.post = getPost();
    }
}
