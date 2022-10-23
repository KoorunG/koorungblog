package com.koorung.blog.exception;

public abstract class CommonException extends RuntimeException{

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getErrorCode();
}
