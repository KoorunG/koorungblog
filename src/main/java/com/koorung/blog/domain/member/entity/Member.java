package com.koorung.blog.domain.member.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.file.entity.File;
import com.koorung.blog.domain.member.exception.PasswordInvalidException;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.global.utils.pwchecker.PasswordChecker;
import com.koorung.blog.global.utils.pwchecker.PasswordStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.koorung.blog.global.utils.pwchecker.PasswordStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String email;

    @Embedded
    private Address address;

    // Member -> File 1:1 관계 (회원은 하나의 프로필사진만 가질 수 있음)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
    private Boolean activated;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String loginId, String password, String username, String email, Address address, File file, Boolean activated, Role role, List<Post> postList) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.address = address;
        this.file = file;
        this.activated = activated;
        // Role을 입력하지 않을 경우 GUEST, 아닐경우 입력한 role
        this.role = role == null ? Role.GUEST : role;
        this.postList = postList;
    }

    // User은 자신이 작성한 글 목록을 볼 수 있어야 한다.
    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    // 유효하지 않은 패스워드가 입력되면 Exception을 발생
    public void checkPassword(String password) {
        PasswordStatus passwordStatus = PasswordChecker.check(password);
        if(passwordStatus == NORMAL || passwordStatus == WEAK || passwordStatus == ERROR) {
            throw new PasswordInvalidException(passwordStatus);
        }
    }
}
