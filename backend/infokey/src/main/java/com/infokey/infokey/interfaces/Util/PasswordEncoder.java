package com.infokey.infokey.interfaces.Util;

public interface PasswordEncoder {
    String encodePassword(String password);
    boolean checkPassword(String candidatePassword, String hashedPassword);
}
