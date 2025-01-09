package org.ufromap.core.utils;

import org.ufromap.core.exceptions.BadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final int MIN_PASSWORD_LENGTH = 8;

    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email is empty");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()){
            throw new BadRequestException("Email contains invalid format");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new BadRequestException("Password is too short");
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (Character.isLowerCase(c)) hasLowerCase = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        if (!hasUpperCase && !hasLowerCase && !hasDigit && !hasSpecialChar) {
            throw new BadRequestException("Password contains invalid characters");
        }
    }
}

