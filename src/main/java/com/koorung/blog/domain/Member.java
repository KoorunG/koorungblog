package com.koorung.blog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotEmpty(message = "이름은 반드시 입력해야 합니다.")
    private String username;

    @NotEmpty(message = "ID는 반드시 입력해야 합니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // User은 자신이 작성한 글 목록을 볼 수 있어야 한다.
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();
}
