package com.infokey.infokey.interfaces.Service;

import java.util.List;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;

public interface IAccountService {
    Response<String> addAccount(String token, AccountForm form);

    Response<String> updateAccount(String token, Account account);
    
    Response<String> deleteAccount(String token, String accountId);

    Response<List<Account>> findUserAccounts(String token);
}
