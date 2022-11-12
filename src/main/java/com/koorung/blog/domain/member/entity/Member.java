package com.koorung.blog.domain.member.entity;

import com.koorung.blog.domain.BaseTimeEntity;
import com.koorung.blog.domain.file.entity.File;
import com.koorung.blog.domain.member.dto.MemberUpdateDto;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.global.utils.pwchecker.PasswordChecker;
import com.koorung.blog.global.utils.pwchecker.PasswordStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 최종 접속일
    private LocalDateTime lastAccessDate;

    @Embedded
    private Address address;

    // Member -> File 1:1 관계 (회원은 하나의 프로필사진만 가질 수 있음)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
    private Boolean activated;
    @Enumerated(EnumType.STRING)
    private Role role;

    // User은 자신이 작성한 글 목록을 볼 수 있어야 한다.
    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String username, String email, Role role, Address address, File file) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.address = address;
        this.file = file;
        // 파라미터로 role이 넘어오지 않으면 GUEST, 아닐경우 role 설정
        this.role = role == null ? Role.GUEST : role;
        // 가입시 lastAccessDate를 현재 일자로 설정
        this.lastAccessDate = LocalDateTime.now();
        // 초기 활성화상태 = true
        this.activated = true;
//        this.postList = new ArrayList<>();
    }

    // ############################# 도메인 비즈니스 로직 ################################# //

    // 유효하지 않은 패스워드가 입력되면 false 리턴
    public PasswordStatus checkPassword(String password) {
        return PasswordChecker.check(password);
    }

    public void updateMember(MemberUpdateDto updateDto) {
        this.username = updateDto.getName();
        this.password = updateDto.getPassword();
        this.email = updateDto.getEmail();
        this.address = Address.builder()
                .city(updateDto.getCity())
                .street(updateDto.getStreet())
                .zipCode(updateDto.getZipCode())
                .build();
    }

    public void configLastAccessDateNow() {
        this.lastAccessDate = LocalDateTime.now();
    }

    public void configActivated(boolean activated) {
        this.activated = activated;
    }

    public void grantAdminRole() {
        this.role = Role.ADMIN;
    }
}
