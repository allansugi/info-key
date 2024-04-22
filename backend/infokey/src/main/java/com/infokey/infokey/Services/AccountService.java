package com.infokey.infokey.Services;

import com.infokey.infokey.Adapter.AccountFormAdapter;
import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AccountDAO dao;
    public AccountService(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Response<String> addAccount(String token, AccountForm form) {
        Response<String> response = new Response<>();
        String userId = JWTUtil.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        AccountFormAdapter adapter = new AccountFormAdapter(form, userId);
        Account account = adapter.convertToAccount();
        this.dao.save(account);
        response.setSuccess(true);
        response.setResponse("Account added");
        return response;
    }

    @Override
    public Response<String> updateAccount(String token, AccountForm form, String accountId) {
        String userId = JWTUtil.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        AccountFormAdapter adapter = new AccountFormAdapter(form, userId);
        Account account = adapter.convertToAccount();
        this.dao.update(account, accountId);
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("account information updated");
        return response;
    }

    @Override
    public Response<String> deleteAccount(String token, String accountId) {
        String userId = JWTUtil.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        this.dao.delete(accountId);
        Response<String> response = new Response<>();
        response.setSuccess(true);
        response.setResponse("account deleted");
        return response;
    }

    @Override
    public Response<List<Account>> findUserAccounts(String token) {
        String userId = JWTUtil.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        List<Account> accounts = this.dao.findByUserId(userId);
        Response<List<Account>> response = new Response<>();
        response.setSuccess(true);
        response.setResponse(accounts);
        return response;
    }
    
}
