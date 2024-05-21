package com.infokey.infokey.interfaces.Controller;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Form.UpdateUserPasswordForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.ViewModel.UserInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserAccountController {
    ResponseEntity<Response<String>> register(@RequestBody RegisterForm form) throws Exception;

    ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res) throws Exception;

    ResponseEntity<Response<UserInfo>> getInfo(@CookieValue String token);

    ResponseEntity<Response<String>> updateUsername(@CookieValue String token, @RequestBody UpdateAccountForm form);
    ResponseEntity<Response<String>> updatePassword(@CookieValue String token, @RequestBody UpdateUserPasswordForm form, HttpServletResponse res);
    ResponseEntity<Response<String>> logout(@CookieValue String token, HttpServletResponse res);
}
