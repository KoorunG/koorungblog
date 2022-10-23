package com.koorung.blog.controller;

import com.koorung.blog.dto.ErrorResponseDto;
import com.koorung.blog.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(CommonException e) {
        return ResponseEntity.status(e.getErrorCode()).body(ErrorResponseDto.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleFieldException(MethodArgumentNotValidException e) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        if (e.hasFieldErrors()) {
            e.getFieldErrors().forEach(fieldError -> {
                errorResponseDto.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        return errorResponseDto;
    }
}
