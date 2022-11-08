package com.koorung.blog.domain.file.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class File extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    // 실제 파일명
    private String fileName;

    // 저장되는 파일명 (난수값)
    private String storedName;

    // 파일 저장 경로
    private String path;

}
