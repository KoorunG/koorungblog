package com.koorung.blog.domain.file.entity;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("IM")
public class Image extends File {
    // 이미지인 경우 너비와 높이 설정
    private Integer width;
    private Integer height;
}
