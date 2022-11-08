package com.koorung.blog.global.utils.pwchecker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PasswordStatus {
    STRONG("매우 안전한 비밀번호입니다.", true),
    GOOD("안전한 비밀번호입니다.", true),
    // TODO) 각 Status에 따른 메세지 수정
    NORMAL("3가지 조건 충족", false),
    WEAK("2가지 조건 충족", false),
    ERROR("1가지 조건 충족", false);

    private final String message;
    private final boolean isValid;
}
