package com.infokey.infokey.interfaces.Service;

import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Response.Response;
import jakarta.servlet.http.HttpServletResponse;

public interface IUserAccountService {
    Response<String> addUser(RegisterForm form);

    Response<String> authenticate(LoginForm form, HttpServletResponse response);

    Response<String> updateUser(UserAccount account);

    Response<String> deleteUser(int id);
}
