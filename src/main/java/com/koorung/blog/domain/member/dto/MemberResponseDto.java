package com.koorung.blog.domain.member.dto;

import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private final Long id;
    private final String loginId;
    private final String password;
    private final String name;
    private final String email;
    private final String city;
    private final String street;
    private final String zipCode;
    private final Role role;


    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.name = member.getUsername();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.city = member.getAddress() != null ? member.getAddress().getCity() : null;
        this.street = member.getAddress() != null ? member.getAddress().getStreet() : null;
        this.zipCode = member.getAddress() != null ? member.getAddress().getZipCode() : null;
    }
}
