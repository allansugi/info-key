package com.infokey.infokey.interfaces.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Response;

import jakarta.servlet.http.HttpServletResponse;

public interface IUserAccountController {
    ResponseEntity<Response<String>> register(@RequestBody RegisterForm form);

    ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res);

    ResponseEntity<Response<String>> updateUsername(@CookieValue("token") String token, @RequestBody UpdateAccountForm form);
}
