package com.infokey.infokey.interfaces.Controller;

import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Response;

import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

public interface IUserAccountController {
    ResponseEntity<Response<String>> register(@RequestBody RegisterForm form) throws SQLException, BadRequestException;

    ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res) throws AuthenticationException, SQLException;

    ResponseEntity<Response<String>> updateUsername(@CookieValue("token") String token, @RequestBody UpdateAccountForm form);
}
