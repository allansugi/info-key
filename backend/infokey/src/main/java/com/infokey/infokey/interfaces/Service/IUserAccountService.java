package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.Response;

public interface IUserAccountService {
    Response<String> addUser(RegisterForm form);

    Response<String> authenticate(LoginForm form);

    Response<String> updatePassword(String token, String password);

    Response<String> updateUsername(String token, String username);
    
    Response<String> updateEmail(String token, String email);
}
