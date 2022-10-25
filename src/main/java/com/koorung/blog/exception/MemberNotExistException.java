package com.koorung.blog.exception;

import org.springframework.http.HttpStatus;

public class MemberNotExistException extends CommonException{

    private static final String MESSAGE = "존재하지 않는 아이디입니다.";
    public MemberNotExistException() {
        super(MESSAGE);
    }

    public MemberNotExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
