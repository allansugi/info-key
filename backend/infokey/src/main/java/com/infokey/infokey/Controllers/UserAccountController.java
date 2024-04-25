package com.infokey.infokey.Controllers;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Services.UserAccountService;
import com.infokey.infokey.interfaces.Controller.IUserAccountController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true" )
@RestController
@RequestMapping("/api/user")
public class UserAccountController implements IUserAccountController {

    private final UserAccountService service;
    public UserAccountController(UserAccountService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody UserAccount account) {
        Response<String> response = service.addUser(account);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res) {
        Response<String> response = service.authenticate(form);
        String token = response.getResponse();
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        res.addCookie(cookie);
        response.setResponse("login success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Methods below have not been implemented yet
     * coming soon!
     */
    @Override
    public ResponseEntity<Response<String>> updateUsername(@CookieValue String token, @RequestBody UpdateAccountForm form) {
        return null;
    }
}
