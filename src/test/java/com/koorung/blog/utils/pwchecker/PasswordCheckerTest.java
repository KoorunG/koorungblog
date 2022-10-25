package com.koorung.blog.utils.pwchecker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1. 길이는 8자이상 12자 미만이어야 한다
 * 2. 하나이상의 특수문자는 반드시 포함되어야한다
 * 3. 하나이상의 알파벳은 반드시 포함되어야한다.
 * 4. 하나이상의 숫자는 반드시 포함되어야한다.
 * <p>
 * 모두 만족할 경우 -> STRONG
 * 세 조건을 만족할 경우 -> GOOD
 * 두 조건만 만족할 경우 -> NORMAL
 * 하나의 조건만 만족할 경우 -> WEAK
 * 하나도 만족하지 않을 경우 -> ERROR
 */
class PasswordCheckerTest {
    private PasswordChecker pwChecker;

    @Test
    @DisplayName("패스워드가 조건을 모두 만족할 경우 STRONG")
    void strong() {
        assertPassword("123ab88!$", PasswordStatus.STRONG);
    }

    @Test
    @DisplayName("패스워드가 8자이상 - 12자 미만 길이조건을 만족하지 않을 경우")
    void good_length() {
        assertPassword("a1234b!", PasswordStatus.GOOD);
    }

    @Test
    @DisplayName("패스워드가 특수문자만 포함하지 않을 경우")
    void good_special() {
        assertPassword("12345abcde", PasswordStatus.GOOD);
    }

    @Test
    @DisplayName("패스워드가 알파벳만 포함하지 않을 경우")
    void good_alpha() {
        assertPassword("12@45!#13", PasswordStatus.GOOD);
    }

    @Test
    @DisplayName("패스워드가 숫자만 포함하지 않을 경우")
    void good_number() {
        assertPassword("assf#@!@", PasswordStatus.GOOD);
    }

    @Test
    @DisplayName("길이조건과 특수문자 조건을 만족하는 경우")
    void normal_length_special() {
        assertPassword("#$!@#!@#$", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("길이조건과 알파벳 조건을 만족하는 경우")
    void normal_length_alpha() {
        assertPassword("asdfadsf", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("길이조건과 숫자 조건을 만족하는 경우")
    void normal_length_number() {
        assertPassword("12321434", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("특수문자과 알파벳 조건을 만족하는 경우")
    void normal_special_alpha() {
        assertPassword("@!ab#", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("특수문자과 숫자 조건을 만족하는 경우")
    void normal_special_number() {
        assertPassword("#123!", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("알파벳과 숫자 조건을 만족하는 경우")
    void normal_alpha_number() {
        assertPassword("alpha3", PasswordStatus.NORMAL);
    }

    @Test
    @DisplayName("길이 조건만 만족하는 경우")
    void weak_length() {
        assertPassword("일이삼사오육칠팔구", PasswordStatus.WEAK);
    }

    @Test
    @DisplayName("특수문자 조건만 만족하는 경우")
    void weak_special() {
        assertPassword("#!@", PasswordStatus.WEAK);
    }

    @Test
    @DisplayName("알파벳 조건만 만족하는 경우")
    void weak_alpha() {
        assertPassword("alp", PasswordStatus.WEAK);
    }

    @Test
    @DisplayName("숫자 조건만 만족하는 경우")
    void weak_number() {
        assertPassword("1231231231233", PasswordStatus.WEAK);
    }

    @Test
    @DisplayName("모두 만족하지 않을 경우")
    void error() {
        assertPassword("ㅎ", PasswordStatus.ERROR);
    }

    private void assertPassword(String password, PasswordStatus status) {
        PasswordStatus passwordStatus = PasswordChecker.check(password);
        Assertions.assertThat(passwordStatus).isEqualTo(status);
    }
}