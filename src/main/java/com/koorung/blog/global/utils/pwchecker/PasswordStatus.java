package com.koorung.blog.global.utils.pwchecker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PasswordStatus {
    STRONG("매우 안전한 비밀번호입니다."),
    GOOD("안전한 비밀번호입니다."),
    NORMAL("비밀번호는 특수문자, 알파벳, 숫자를 적어도 하나씩 포함해야 하며 길이는 8자 이상 12자 이하여야 합니다."),
    WEAK("비밀번호는 특수문자, 알파벳, 숫자를 적어도 하나씩 포함해야 하며 길이는 8자 이상 12자 이하여야 합니다."),
    ERROR("비밀번호는 특수문자, 알파벳, 숫자를 적어도 하나씩 포함해야 하며 길이는 8자 이상 12자 이하여야 합니다.");

    private final String message;
}
