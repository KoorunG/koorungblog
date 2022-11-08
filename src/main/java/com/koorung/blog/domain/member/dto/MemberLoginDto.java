package com.koorung.blog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * PackageName : com.koorung.blog.domain.member.dto
 * FileName : MemberLoginDto
 * Author : Koorung
 * Date : 2022년 11월 09일
 * Description :
 */
@Getter
public class MemberLoginDto {
    private final String loginId;
    private final String password;

    @Builder
    public MemberLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
