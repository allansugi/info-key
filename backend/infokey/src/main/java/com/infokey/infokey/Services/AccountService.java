package com.infokey.infokey.Services;

import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Mapper.AccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AccountDAO dao;
    private final JWTUtil jwt;
    private final AccountMapper mapper;
    public AccountService(AccountDAO dao, JWTUtil jwt, AccountMapper mapper) {
        this.dao = dao;
        this.jwt = jwt;
        this.mapper = mapper;
    }

    @Override
    public Response<String> addAccount(String token, AccountForm form) {
        String userId = jwt.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        Account account = mapper.toDTO(form, userId);
        this.dao.save(account);
        return new Response<>(true, "Account added");
    }

    @Override
    public Response<String> updateAccount(String token, AccountForm form, String accountId) {
        String userId = jwt.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        Account account = mapper.toDTO(form, userId);
        this.dao.update(account, accountId);
        return new Response<>(true, "account information updated");
    }

    @Override
    public Response<String> deleteAccount(String token, String accountId) {
        String userId = jwt.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        this.dao.delete(accountId);
        return new Response<>(true, "account deleted");
    }

    @Override
    public Response<List<Account>> findUserAccounts(String token) {
        String userId = jwt.verifyToken(token);
        Assert.notNull(userId, "empty token or token cannot be verified");
        List<Account> accounts = this.dao.findByUserId(userId);
        return new Response<>(true, accounts);
    }
    
}
