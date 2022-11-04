package com.koorung.blog.domain.member.dto;

import com.koorung.blog.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberCreateDto {

    private String name;

    @NotEmpty(message = "ID는 반드시 입력해야 합니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public MemberCreateDto(String name, String id, String password, Role role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
    }
}
