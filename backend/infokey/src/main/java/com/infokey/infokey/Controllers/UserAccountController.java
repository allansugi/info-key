package com.infokey.infokey.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Response;

import jakarta.servlet.http.HttpServletResponse;

public interface UserAccountController {
    public ResponseEntity<Response<String>> register(@RequestBody RegisterForm form);

    public ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res);

    @PutMapping("/update/account")
    @ResponseBody
    ResponseEntity<Response<String>> updateUsername(@CookieValue("token") String token, @RequestBody UpdateAccountForm form);
}
