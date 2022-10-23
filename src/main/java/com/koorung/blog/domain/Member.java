package com.koorung.blog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty(message = "이름은 반드시 입력해야 합니다.")
    private String username;

//    @NotEmpty(message = "ID는 반드시 입력해야 합니다.")
    private String loginId;

//    @NotEmpty(message = "비밀번호는 반드시 입력해야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String loginId, String password, Role role) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    // User은 자신이 작성한 글 목록을 볼 수 있어야 한다.
    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();
}
