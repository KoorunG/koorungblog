package com.koorung.blog.domain.comment.exception;

import com.koorung.blog.global.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * PackageName : com.koorung.blog.domain.comment.exception
 * FileName : CommentNotExistException
 * Author : Koorung
 * Date : 2022년 11월 13일
 * Description : Comment가 존재하지 않을 때 발생하는 Exception
 */
public class CommentNotExistException extends CommonException {

    private static final String MESSAGE = "댓글이 존재하지 않습니다.";

    public CommentNotExistException() {
        super(MESSAGE);
    }

    public CommentNotExistException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
