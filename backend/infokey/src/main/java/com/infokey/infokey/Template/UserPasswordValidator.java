package com.infokey.infokey.Template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPasswordValidator extends PasswordValidator {

    public UserPasswordValidator(String password) {
        this.password = password;
    }

    @Override
    public boolean specialCharacterCheck(String password) {
        Pattern special = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\\\[\\\\]~-]");
        Matcher hasSpecial = special.matcher(password);
        return hasSpecial.find();
    }

    @Override
    public boolean numberCheck(String password) {
       Pattern digit = Pattern.compile("[0-9]");
       Matcher hasDigit = digit.matcher(password);
       return hasDigit.find();
    }

    @Override
    public boolean letterCheck(String password) {
        Pattern letter = Pattern.compile("[a-zA-Z]");
        Matcher hasLetter = letter.matcher(password);
        return hasLetter.find();
    }
    
}
