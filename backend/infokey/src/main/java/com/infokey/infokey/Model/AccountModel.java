package com.infokey.infokey.Model;

public class AccountModel {
    private String id;
    private String account;
    private String username;
    private String password;

    public AccountModel(String id, String account, String username, String password) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
