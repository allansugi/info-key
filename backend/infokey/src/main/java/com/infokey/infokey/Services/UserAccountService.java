package com.infokey.infokey.Services;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.Exceptions.IllegalRegisterException;
import com.infokey.infokey.Exceptions.LoginNotFoundException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.Template.UserPasswordValidator;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    private final UserDAO dao;

    @Autowired
    public UserAccountService(UserDAO dao) {
        this.dao = dao;
    }
    

    @Override
    public Response<String> addUser(RegisterForm form) throws SQLException,IllegalRegisterException {
        try {
            Response<String> response = new Response<>();
            UserAccount account = new UserAccount(form);
            UserPasswordValidator validator = new UserPasswordValidator(account.getPassword());
            
            if (!validator.validPassword()) {
                throw new IllegalRegisterException("Password does not meet the requirement");
            } else {
                dao.save(account);
                response.setSuccess(true);
                response.setResponse("Registration successful");
                return response;
            }
            
        } catch (SQLException e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public Response<String> authenticate(LoginForm form) throws LoginNotFoundException, SQLException {
         try {
            List<UserAccount> users = this.dao.findAll();
            String valId = form.getUsername();
            List<UserAccount> validUser = users.stream()
                    .filter(u -> u.getEmail().equals(valId))
                    .toList();
            if (!validUser.isEmpty() && form.getPassword().equals(validUser.get(0).getPassword())) {
                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse(JWTUtil.createToken(validUser.get(0).getId()));
                return response;
            } else {
                throw new LoginNotFoundException("Incorrect email or password");
            }
        } catch (SQLException e) {
            throw new SQLException("Database connection error");
        }
    }

    /**
     * methods below has not been implemented
     * coming soon!
     */

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
