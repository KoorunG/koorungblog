package com.koorung.blog.domain.member.dto;

import com.koorung.blog.domain.member.entity.Address;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class MemberCreateDto {

    @NotEmpty(message = "이름은 반드시 입력해야 합니다.")
    private final String name;

    @NotEmpty(message = "ID는 반드시 입력해야 합니다.")
    private final String id;

    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private final String password;

    @Email(message = "올바른 형식의 이메일을 입력해야 합니다.")
    private final String email;

    private final Address address;

    @Builder
    public MemberCreateDto(String name, String id, String password, String email, String city, String street, String zipCode) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.address = Address.builder()
                .city(city)
                .street(street)
                .zipCode(zipCode).build();
    }
}
