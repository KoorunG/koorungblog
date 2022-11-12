package com.koorung.blog.global.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * PackageName : com.koorung.blog.global.dto
 * FileName : Result
 * Author : Koorung
 * Date : 2022년 11월 12일
 * Description : 응답 JSON을 Wrapping 하는 객체
 */
@Getter
public class Result<T> {

    private int count;
    private final List<T> data = new ArrayList<>();

    public void addData(T t) {
        data.add(t);
        this.count = data.size();
    }

    public void addData(List<T> list) {
        data.addAll(list);
        this.count = data.size();
    }
}
