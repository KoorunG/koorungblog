package com.koorung.blog.global.utils.pwchecker;

import java.util.regex.Pattern;

import static com.koorung.blog.global.utils.pwchecker.PasswordStatus.*;

public class PasswordChecker {
    public static PasswordStatus check(String password) {
        int successCount = getSuccessCount(password);
        return getPasswordStatus(successCount);
    }

    private static PasswordStatus getPasswordStatus(int successCount) {
        switch (successCount) {
            case 4:
                return STRONG;
            case 3:
                return GOOD;
            case 2:
                return NORMAL;
            case 1:
                return WEAK;
            default:
                return ERROR;
        }
    }

    private static int getSuccessCount(String password) {
        int successCount = 0;

        if(lengthCondition(password)) {
            successCount++;
        }
        if(specialCondition(password)) {
            successCount++;
        }
        if(numberCondition(password)) {
            successCount++;
        }
        if(alphaCondition(password)) {
            successCount++;
        }
        return successCount;
    }

    private static boolean lengthCondition(String password) {
        return password.length() >= 8 && password.length() < 12;
    }

    private static boolean specialCondition(String password) {
        Pattern pattern = Pattern.compile(".*[~!@#$%^&*()_+|<>?:{}].*");
        return pattern.matcher(password).matches();
    }

    private static boolean alphaCondition(String password) {
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
        return pattern.matcher(password).matches();
    }

    private static boolean numberCondition(String password) {
        Pattern pattern = Pattern.compile(".*[0-9].*");
        return pattern.matcher(password).matches();
    }
}
