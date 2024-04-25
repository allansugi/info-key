package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.DTO.UserAccount;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;

public interface IUserAccountService {
    Response<String> addUser(RegisterForm form) throws BadRequestException;

    Response<String> authenticate(LoginForm form) throws AuthenticationException;

    Response<String> updateUser(UserAccount account);

    Response<String> deleteUser(int id);
}
