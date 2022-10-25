package com.koorung.blog.domain;

import com.koorung.blog.exception.PasswordInvalidException;
import com.koorung.blog.utils.pwchecker.PasswordChecker;
import com.koorung.blog.utils.pwchecker.PasswordStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.koorung.blog.utils.pwchecker.PasswordStatus.*;

@Entity
@Getter
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String loginId;

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

    public Member() {
        this.role = Role.GUEST;
    }

    // User은 자신이 작성한 글 목록을 볼 수 있어야 한다.
    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    public void checkPassword(String password) {
        PasswordStatus passwordStatus = PasswordChecker.check(password);
        if(passwordStatus == NORMAL || passwordStatus == WEAK || passwordStatus == ERROR) {
            throw new PasswordInvalidException(passwordStatus);
        }
    }
}
