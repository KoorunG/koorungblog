package com.koorung.blog.domain;

public enum Role {
    GUEST, // GUEST 권한 : 방명록 글쓰기 가능
    ADMIN, // ADMIN 권한 : 모든 권한
    USER   // USER 권한 : 글쓰기, 글삭제, 기타 등등..
}
