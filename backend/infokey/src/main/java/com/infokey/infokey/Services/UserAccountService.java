package com.infokey.infokey.Services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.Template.UserPasswordValidator;
import com.infokey.infokey.interfaces.Service.IUserAccountService;

import lombok.val;

public class UserAccountService implements IUserAccountService {

    private UserDAO dao;

    @Autowired
    public UserAccountService(UserDAO dao) {
        this.dao = dao;
    }
    

    @Override
    public Response<String> addUser(RegisterForm form) {
        Response<String> response = new Response<>();

        try {
            UserAccount account = new UserAccount(form);
            UserPasswordValidator validator = new UserPasswordValidator(account.getPassword());
            
            if (!validator.validPassword()) {
                response.setSuccess(false);
                response.setResponse("Invalid password");
            } else {
                dao.save(account);
                response.setSuccess(true);
                response.setResponse("Registration successful");
            }
            
        } catch (SQLException e) {
            response.setSuccess(false);
            response.setResponse("Database connection error");
        }

        return response;
    }

    @Override
    public Response<String> authenticate(LoginForm form) {
         try {
            List<UserAccount> users = this.dao.findAll();
            String valId = form.getUsername();
            List<UserAccount> validUser = users.stream()
                    .filter(u -> u.getEmail().equals(valId))
                    .toList();
            if (!validUser.isEmpty() && passwordMatches(form.getPassword(), validUser.get(0).getPassword())) {
                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse(this.util.createToken(validUser.get(0).getId()));
                return response;
            } else {
                throw new AuthenticationException("Incorrect email or password");
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> updatePassword(String token, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }

    @Override
    public Response<String> updateUsername(String token, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUsername'");
    }

    @Override
    public Response<String> updateEmail(String token, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEmail'");
    }
    
}
