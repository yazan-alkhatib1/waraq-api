package com.waraq.service.helpers.validators;

public class PasswordValidator {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,15}$";

    public static Boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
