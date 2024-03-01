package com.infokey.infokey.Form;

public class UpdateAccountForm extends AccountForm {
    private String id;
    private String userId;

    public UpdateAccountForm() {
        // Default constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
