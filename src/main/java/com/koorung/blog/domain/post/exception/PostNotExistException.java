package com.koorung.blog.domain.post.exception;

import com.koorung.blog.global.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PostNotExistException extends CommonException {

    private static final String MESSAGE = "글이 존재하지 않습니다!";
    public PostNotExistException() {
        super(MESSAGE);
    }

    public PostNotExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
