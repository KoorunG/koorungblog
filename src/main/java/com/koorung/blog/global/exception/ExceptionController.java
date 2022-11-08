package com.koorung.blog.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    /**
     * 커스텀 에러를 핸들링하는 메소드
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CommonException e) {
        return ResponseEntity.status(e.getErrorCode()).body(ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .build());
    }

    /**
     * 필드 에러를 핸들링하는 메소드
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleFieldException(MethodArgumentNotValidException e) {

        // 1. 필드에러일 경우 errorCode = 400, message = "필드 에러 발생" 을 명시하고
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message("필드 에러 발생")
                .build();

        // 2. 필드에러 내용을 validation 으로 저장한다.
        if (e.hasFieldErrors()) {
            e.getFieldErrors().forEach(fieldError -> {
                errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
