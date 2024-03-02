package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.Response;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;

import java.sql.SQLException;

public interface IUserAccountService {
    Response<String> addUser(RegisterForm form) throws SQLException, BadRequestException;

    Response<String> authenticate(LoginForm form) throws AuthenticationException, SQLException;

    Response<String> updatePassword(String token, String password);

    Response<String> updateUsername(String token, String username);
    
    Response<String> updateEmail(String token, String email);
}
