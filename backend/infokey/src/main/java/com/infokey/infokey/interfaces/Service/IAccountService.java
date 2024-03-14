package com.infokey.infokey.interfaces.Service;

import java.sql.SQLException;
import java.util.List;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.ViewModel.AccountViewModel;

public interface IAccountService {
    Response<String> addAccount(String token, AccountForm form);

    Response<String> updateAccount(String token, UpdateAccountForm account) throws SQLException;
    
    Response<String> deleteAccount(String token, String accountId) throws SQLException;

    Response<List<AccountViewModel>> findUserAccounts(String token) throws SQLException;
}
