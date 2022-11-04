package com.koorung.blog.domain.member.exception;

import com.koorung.blog.global.exception.CommonException;
import com.koorung.blog.global.utils.pwchecker.PasswordStatus;
import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends CommonException {

    public PasswordInvalidException(PasswordStatus passwordStatus) {
        super(passwordStatus.getMessage());
    }

    public PasswordInvalidException(PasswordStatus passwordStatus, Throwable cause) {
        super(passwordStatus.getMessage(), cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
