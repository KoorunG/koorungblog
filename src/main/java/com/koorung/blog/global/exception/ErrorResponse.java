package com.koorung.blog.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private int errorCode;
    private String message;
    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}


