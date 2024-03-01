package com.infokey.infokey.Services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IAccountService;

public class AccountService implements IAccountService {

    private AccountDAO dao;

    @Autowired
    public AccountService(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Response<String> addAccount(String token, AccountForm form) {
        Response<String> response = new Response<>();
        try {
            String userId = JWTUtil.verifyToken(token);
            Account account = new Account(userId, form);
            this.dao.save(account);
            
            response.setSuccess(true);
            response.setResponse("Account added");
        } catch (SQLException e) {
            response.setSuccess(false);
            response.setResponse("Database Connection Error");
        }
        return response;
    }

    @Override
    public Response<String> updateAccount(String token, Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    @Override
    public Response<String> deleteAccount(String token, String accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAccount'");
    }

    @Override
    public Response<List<Account>> findUserAccounts(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserAccounts'");
    }
    
}
