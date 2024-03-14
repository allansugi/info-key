package com.infokey.infokey.Form;

public class UpdateAccountForm extends AccountForm {
    private String id;

    public UpdateAccountForm() {
        // Default constructor
        super();
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
