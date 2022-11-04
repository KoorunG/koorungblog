package com.koorung.blog.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CommonException e) {
        return ResponseEntity.status(e.getErrorCode()).body(ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFieldException(MethodArgumentNotValidException e) {

        ErrorResponse errorResponseDto = new ErrorResponse();

        if (e.hasFieldErrors()) {
            e.getFieldErrors().forEach(fieldError -> {
                errorResponseDto.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        return errorResponseDto;
    }
}
