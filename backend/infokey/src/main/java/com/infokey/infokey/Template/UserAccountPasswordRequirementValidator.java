package com.infokey.infokey.Template;

/**
 * password requirements:
 * 1. lowercase letter
 * 2. uppercase letter
 * 3. special character
 * 4. minimum length of 8
 */
public class UserAccountPasswordRequirementValidator {
    public static boolean isValid(String password) {
        boolean validLength = password.length() >= 8 && password.length() <= 20;
        boolean containDigits = password.matches(".*\\d.*");
        boolean containUppercase = password.matches(".*[A-Z].*");
        boolean containLowercase = password.matches(".*[a-z].*");
        boolean containSpecialCharacter = password.matches(".*[^A-Za-z0-9].*");

        return validLength && containDigits &&
                containUppercase && containLowercase &&
                containSpecialCharacter;
    }
}
