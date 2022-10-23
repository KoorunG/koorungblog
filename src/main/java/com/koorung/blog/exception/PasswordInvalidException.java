package com.koorung.blog.exception;

import com.koorung.blog.utils.pwchecker.PasswordStatus;
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
