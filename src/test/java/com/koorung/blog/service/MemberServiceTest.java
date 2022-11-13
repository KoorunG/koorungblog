package com.koorung.blog.service;

import com.koorung.blog.domain.member.application.MemberService;
import com.koorung.blog.domain.member.dto.MemberCreateDto;
import com.koorung.blog.domain.member.dto.MemberLoginDto;
import com.koorung.blog.domain.member.dto.MemberUpdateDto;
import com.koorung.blog.domain.member.entity.Member;
import com.koorung.blog.domain.member.entity.Role;
import com.koorung.blog.domain.member.exception.AlreadyExistMemberException;
import com.koorung.blog.domain.member.exception.MemberNotExistException;
import com.koorung.blog.domain.member.exception.PasswordInvalidException;
import com.koorung.blog.domain.member.repository.MemberRepository;
import com.koorung.blog.domain.post.entity.Post;
import com.koorung.blog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("회원가입시 비밀번호가 조건에 맞지 않으면 예외 발생")
    void join_password_fail() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("koorung1234")
                .password("1234")
                .name("쿠렁")
                .build();

        // then
        assertThrows(PasswordInvalidException.class, () -> memberService.join(memberCreateDto));
    }

    @Test
    @DisplayName("관리자 회원가입 성공")
    void join_admin_success() {

        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("admin1234")
                .password("test1234kk!")
                .name("관리자")
                .build();

        //when
        Long memberId = memberService.join(memberCreateDto);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistException::new);
        member.grantAdminRole();

        //then
        assertThat(member).isNotNull();
        assertThat(member).extracting("role").isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("일반 유저 회원가입 성공")
    void join_user_success() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .password("user1234@!")
                .name("일반유저")
                .city("남양주")
                .street("진관로")
                .zipCode("12345")
                .email("koorung@naver.com")
                .build();

        //when
        Long memberId = memberService.join(memberCreateDto);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotExistException::new);

        //then
        assertThat(member).isNotNull();
        assertThat(member).extracting("role").isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("회원가입 하지 않은 유저의 권한은 GUEST")
    void none_join_guest() {
        //given
        Member member = Member.builder()
                .role(Role.GUEST)
                .build();
        //then
        assertThat(member).extracting("role").isEqualTo(Role.GUEST);
    }

    @Test
    @DisplayName("가입되지 않은 유저정보로 로그인할 때 예외 발생")
    void login_fail_not_exist_user() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .password("user1234@!")
                .name("일반유저")
                .build();

        memberService.join(memberCreateDto);

        //when
        assertThrows(MemberNotExistException.class, () -> {
            memberService.login(MemberLoginDto.builder()
                    .loginId("adfs")
                    .password("sdf").build());
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        //given
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .id("user1234")
                .password("user1234@!")
                .name("일반유저")
                .build();

        memberService.join(memberCreateDto);

        //when
        Member loginUser = memberService.login(MemberLoginDto.builder()
                .loginId("user1234")
                .password("user1234@!").build());

        //then
        assertThat(loginUser).extracting("username").isEqualTo("일반유저");
        assertThat(loginUser).extracting("loginId").isEqualTo("user1234");
        assertThat(loginUser).extracting("password").isEqualTo("user1234@!");
    }

    @Test
    @DisplayName("회원가입시 중복되는 로그인 아이디가 존재할 경우 예외 발생")
    void join_fail_already_exist_loginId() {
        //given
        MemberCreateDto createDto = MemberCreateDto.builder()
                .id("koorung")
                .password("test1234!@")
                .name("쿠렁")
                .build();

        memberService.join(createDto);

        //when
        assertThrows(AlreadyExistMemberException.class, () -> {
            // 동일 요청으로 가입을 시도하면 에러 발생
            memberService.join(createDto);
        });
    }

    @Test
    @DisplayName("모든 회원 찾기")
    void find_all_members() {
//        //given
        List<Member> memberList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Member.builder()
                        .loginId("test" + i)
                        .password("test!@#$" + i)
                        .username("테스트" + i).build())
                .collect(Collectors.toList());
        memberRepository.saveAll(memberList);

        //when
        List<Member> members = memberService.findAllMember();

        //then
        assertThat(members.size()).isEqualTo(5);
        assertThat(members).extracting("username").containsExactly("테스트1", "테스트2", "테스트3", "테스트4", "테스트5");
    }

    @Test
    @DisplayName("회원의 정보 수정 테스트")
    void modifyMemberInfo() {
        //given
        List<Member> memberList = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> Member.builder()
                        .loginId("test" + i)
                        .password("test!@#$" + i)
                        .username("테스트" + i).build())
                .collect(Collectors.toList());
        memberRepository.saveAll(memberList);

        Long id = memberList.get(0).getId();

        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .name("이름수정테스트")
                .password("Test132!@")
                .email("modifytest@test.com")
                .build();
        //when
        Member findMember = memberRepository.findById(id).orElseThrow(MemberNotExistException::new);
        Member updatedMember = memberService.modifyMemberInfo(findMember.getId(), updateDto);

        //then
        assertThat(updatedMember).extracting("username").isEqualTo("이름수정테스트");
        assertThat(updatedMember).extracting("password").isEqualTo("Test132!@");
        assertThat(updatedMember).extracting("email").isEqualTo("modifytest@test.com");
    }

    @Test
    @DisplayName("유저가 쓴 글을 모두 조회")
    void get_all_posts() {
        //given

        Member savedMember = memberRepository.save(Member.builder()
                .loginId("koorung")
                .username("쿠렁")
                .password("test1234!@")
                .role(Role.ADMIN).build());

        List<Post> postList = IntStream.rangeClosed(1, 10).mapToObj(i -> Post.builder()
                .title("제목" + i)
                .contents("내용" + i).build()).collect(Collectors.toList());

        postList.forEach((post) -> {
            post.configMember(savedMember);
        });

        postRepository.saveAll(postList);

        //when

        //then

    }
}
