package com.koorung.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {

    private int errorCode;
    private String message;
    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponseDto(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}


