package com.infokey.infokey.Services;

import com.infokey.infokey.Adapter.AccountFormAdapter;
import com.infokey.infokey.Adapter.UpdateAccountFormAdapter;
import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.ViewModel.AccountViewModel;
import com.infokey.infokey.interfaces.Service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    private final AccountDAO dao;

    @Autowired
    public AccountService(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Response<String> addAccount(String token, AccountForm form) {
        Response<String> response = new Response<>();
        try {
            String userId = JWTUtil.verifyToken(token);
            AccountFormAdapter adapter = new AccountFormAdapter(form, userId);
            Account account = adapter.convertToAccount();
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
    public Response<String> updateAccount(String token, UpdateAccountForm account) throws SQLException {
        try {
            String userId = JWTUtil.verifyToken(token);
            UpdateAccountFormAdapter adapter = new UpdateAccountFormAdapter(userId, account);
            Account convertAccount = adapter.convertToAccount();
            this.dao.update(convertAccount);

            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("account information updated");

            return response;
        } catch (SQLException e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public Response<String> deleteAccount(String token, String accountId) throws SQLException {
        try {
            String userId = JWTUtil.verifyToken(token);
            this.dao.delete(accountId);

            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("account deleted");

            return response;
        } catch (SQLException e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public Response<List<AccountViewModel>> findUserAccounts(String token) throws SQLException {
        try {
            String userId = JWTUtil.verifyToken(token);
            List<AccountViewModel> accounts = this.dao.findByUserId(userId);

            Response<List<AccountViewModel>> response = new Response<>();
            response.setSuccess(true);
            response.setResponse(accounts);

            return response;
        } catch (SQLException e) {
            throw new SQLException("Database connection error");
        }
    }
    
}
