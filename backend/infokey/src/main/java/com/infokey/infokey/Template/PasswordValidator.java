package com.infokey.infokey.Template;

public abstract class PasswordValidator {

    String password;
    
    public PasswordValidator() {
        
    }

    public boolean validPassword() {
        if (password.length() >= 8) {
            return specialCharacterCheck(this.password) &&
                    numberCheck(this.password) &&
                    letterCheck(this.password);
        } else {
            return false;
        }
    }

    public abstract boolean specialCharacterCheck(String password);

    public abstract boolean numberCheck(String password);

    public abstract boolean letterCheck(String password);
}
