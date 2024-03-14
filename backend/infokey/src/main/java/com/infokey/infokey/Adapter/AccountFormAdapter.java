package com.infokey.infokey.Adapter;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;

import java.util.UUID;

public class AccountFormAdapter {
    private final AccountForm form;
    private final String userId;

    public AccountFormAdapter(AccountForm form, String userId) {
        this.form = form;
        this.userId = userId;
    }

    public Account convertToAccount() {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setUserId(userId);
        account.setAccount_username(form.getAccountUsername());
        account.setAccount_name(form.getAccountName());
        account.setAccount_password(form.getAccountPassword());

        return account;
    }
}
