package com.infokey.infokey.Model;

import java.util.UUID;

import com.infokey.infokey.Form.RegisterForm;

public class UserAccount {
    private String id;
    private String username;
    private String email;
    private String password;

    public UserAccount() {
        // Default constructor
    }


    /**
     * convert register form to user account to store into database
     * @param form
     */
    public UserAccount(RegisterForm form) {
        this.id = UUID.randomUUID().toString();
        this.username = form.getUsername();
        this.email = form.getEmail();
        this.password = form.getPassword();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
