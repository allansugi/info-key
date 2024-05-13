package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.Response.Response;

import java.util.List;

public interface IAccountService {

    Response<String> addAccount(String token, AccountForm account);

    Response<String> updateAccount(String token, AccountForm account, String accountId);

    Response<String> deleteAccount(String token, String accountId);

    Response<List<Account>> findUserAccounts(String token);
}
