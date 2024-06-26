package com.infokey.infokey.DTO;

public class Account {
    private String id;
    private String userId;
    private String account_name;
    private String account_username;
    private String account_password;

    public Account(String id, String userId, String account_name, String account_username, String account_password) {
        this.id = id;
        this.userId = userId;
        this.account_name = account_name;
        this.account_username = account_username;
        this.account_password = account_password;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_username() {
        return account_username;
    }

    public void setAccount_username(String account_username) {
        this.account_username = account_username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
