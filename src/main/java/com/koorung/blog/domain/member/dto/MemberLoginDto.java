package com.koorung.blog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * PackageName : com.koorung.blog.domain.member.dto
 * FileName : MemberLoginDto
 * Author : Koorung
 * Date : 2022년 11월 09일
 * Description :
 */
@Getter
public class MemberLoginDto {

    @NotEmpty(message = "아이디는 반드시 입력해야 합니다.")
    private final String loginId;
    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private final String password;

    @Builder
    public MemberLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
