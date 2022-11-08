package com.koorung.blog.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateDto {
    private final String password;
    private final String name;
    private final String email;
    private final String city;
    private final String street;
    private final String zipCode;

    @Builder
    public MemberUpdateDto(String password, String name, String email, String city, String street, String zipCode) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
