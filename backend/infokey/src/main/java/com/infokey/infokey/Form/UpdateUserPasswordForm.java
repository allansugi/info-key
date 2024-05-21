package com.infokey.infokey.Form;

public class UpdateUserPasswordForm {
    private String userInputMasterPassword;
    private String newPassword;

    public UpdateUserPasswordForm(String userInputMasterPassword, String newPassword) {
        this.userInputMasterPassword = userInputMasterPassword;
        this.newPassword = newPassword;
    }

    public String getUserInputMasterPassword() {
        return userInputMasterPassword;
    }

    public void setUserInputMasterPassword(String userInputMasterPassword) {
        this.userInputMasterPassword = userInputMasterPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
