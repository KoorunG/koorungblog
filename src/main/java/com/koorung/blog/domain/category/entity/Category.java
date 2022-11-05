package com.koorung.blog.domain.category.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.relation.CategoryPost;
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
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryPost> categoryPosts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    public void configParent(Category parent) {
        this.parent = parent;
    }

    public void addChildren(Category child) {
        child.configParent(this);
        children.add(child);
    }

    @Column(name = "category_level")
    private Integer level;

    @Column(name = "category_order")
    private Integer order;

    @Builder
    public Category(String name, List<CategoryPost> categoryPosts, Integer order) {
        this.name = name;
        this.categoryPosts = categoryPosts;
        // 초기 레벨은 0, depth에 따라 레벨 1씩 증가
        this.level = 0;
        this.order = order;
    }
}
