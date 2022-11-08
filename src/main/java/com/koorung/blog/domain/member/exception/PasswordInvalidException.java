package com.koorung.blog.domain.member.exception;

import com.koorung.blog.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends CommonException {

    public PasswordInvalidException(String message) {
        super(message);
    }

    public PasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
