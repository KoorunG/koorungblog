package com.koorung.blog.utils.pwchecker;

import java.util.regex.Pattern;

import static com.koorung.blog.utils.pwchecker.PasswordStatus.*;

public class PasswordChecker {
    public PasswordStatus check(String password) {
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

        PasswordStatus passwordStatus;

        switch (successCount) {
            case 4:
                passwordStatus = STRONG;
                break;
            case 3:
                passwordStatus = GOOD;
                break;
            case 2:
                passwordStatus = NORMAL;
                break;
            case 1:
                passwordStatus = WEAK;
                break;
            default:
                passwordStatus = ERROR;
                break;
        }

        return passwordStatus;
    }
    private boolean lengthCondition(String password) {
        return password.length() >= 8 && password.length() < 12;
    }

    private boolean specialCondition(String password) {
        Pattern pattern = Pattern.compile(".*[~!@#$%^&*()_+|<>?:{}].*");
        return pattern.matcher(password).matches();
    }

    private boolean alphaCondition(String password) {
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
        return pattern.matcher(password).matches();
    }

    private boolean numberCondition(String password) {
        Pattern pattern = Pattern.compile(".*[0-9].*");
        return pattern.matcher(password).matches();
    }
}
