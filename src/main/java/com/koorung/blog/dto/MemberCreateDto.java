package com.koorung.blog.dto;

import com.koorung.blog.domain.Role;
import com.koorung.blog.exception.PasswordInvalidException;
import com.koorung.blog.utils.pwchecker.PasswordChecker;
import com.koorung.blog.utils.pwchecker.PasswordStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import static com.koorung.blog.utils.pwchecker.PasswordStatus.ERROR;
import static com.koorung.blog.utils.pwchecker.PasswordStatus.WEAK;

@Getter
@Setter
public class MemberCreateDto {

    private String name;

    @NotEmpty(message = "ID는 반드시 입력해야 합니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;

    private Role role;

    @Builder
    public MemberCreateDto(String name, String id, String password, Role role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.role = role;
    }
}
