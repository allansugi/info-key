package com.infokey.infokey.Adapter;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;

public class AccountAdapter {
    private final Account account;

    public AccountAdapter(Account account) {
        this.account = account;
    }

    public AccountForm convertToAccountForm() {
        AccountForm accountForm = new AccountForm();
        accountForm.setAccountName(account.getAccount_name());
        accountForm.setAccountUsername(account.getAccount_username());
        accountForm.setAccountPassword(account.getAccount_password());

        return accountForm;
    }
}
