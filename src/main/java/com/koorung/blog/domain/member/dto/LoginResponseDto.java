package com.koorung.blog.domain.member.dto;

import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import lombok.Getter;

/**
 * PackageName : com.koorung.blog.domain.member.dto
 * FileName : LoginResponseDto
 * Author : Koorung
 * Date : 2022년 11월 12일
 * Description : 로그인 응답 객체
 */

@Getter
public class LoginResponseDto {

    private final Long id;
    private final String username;
    private final Role role;

    public LoginResponseDto (Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.role = member.getRole();
    }
}
