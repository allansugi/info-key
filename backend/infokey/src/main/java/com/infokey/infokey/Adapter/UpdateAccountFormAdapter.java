package com.infokey.infokey.Adapter;

import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Account;

public class UpdateAccountFormAdapter {
    private final String userId;
    private final UpdateAccountForm form;

    public UpdateAccountFormAdapter(String userId, UpdateAccountForm form) {
        this.userId = userId;
        this.form = form;
    }

    public Account convertToAccount() {
        Account account = new Account();
        account.setId(form.getId());
        account.setUserId(userId);
        account.setAccount_name(form.getAccountName());
        account.setAccount_username(form.getAccountUsername());
        account.setAccount_password(form.getAccountPassword());
        return account;
    }
}
