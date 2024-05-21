package com.infokey.infokey.Util;

import com.infokey.infokey.interfaces.Util.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String candidatePassword, String hashedPassword) {
        return BCrypt.checkpw(candidatePassword, hashedPassword);
    }


}
