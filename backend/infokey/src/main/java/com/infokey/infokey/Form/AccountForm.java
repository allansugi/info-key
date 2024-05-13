package com.infokey.infokey.Form;

public class AccountForm {
    private String accountName;
    private String accountUsername;
    private String accountPassword;

    public AccountForm(String accountName, String accountUsername, String accountPassword) {
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String toString() {
        return "account name: " + getAccountName() + " account username: " + getAccountUsername() + " account password: " + getAccountPassword();
    }
}
