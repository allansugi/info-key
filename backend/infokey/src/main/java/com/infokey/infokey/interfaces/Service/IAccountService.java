package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;

import java.util.List;

public interface IAccountService {
    Response<String> addAccount(String token, AccountForm form);

    Response<String> updateAccount(String token, AccountForm account, String accountId);

    Response<String> deleteAccount(String token, String accountId);

    Response<List<Account>> findUserAccounts(String token);
}
