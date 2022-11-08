package com.koorung.blog.domain.member.exception;

import com.koorung.blog.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AlreadyExistMemberException extends CommonException {

    private static final String MESSAGE = "이미 존재하는 회원입니다!";

    public AlreadyExistMemberException() {
        super(MESSAGE);
    }

    public AlreadyExistMemberException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
