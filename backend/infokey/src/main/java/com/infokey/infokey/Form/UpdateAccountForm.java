package com.infokey.infokey.Form;

public class UpdateAccountForm extends AccountForm {
    private String id;

    public UpdateAccountForm(String accountName, String accountUsername, String accountPassword, String id) {
        super(accountName, accountUsername, accountPassword);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "id: " + getId() +
                "accountName: " + getAccountName() +
                "accountUsername: " + getAccountUsername() +
                "accountPassword: " + getAccountPassword();

    }
}
