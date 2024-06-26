package com.infokey.infokey.Controllers;

import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Form.UpdateUserPasswordForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.UserAccountService;
import com.infokey.infokey.ViewModel.UserInfo;
import com.infokey.infokey.interfaces.Controller.IUserAccountController;
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
    public ResponseEntity<Response<String>> register(@RequestBody RegisterForm form) throws Exception {
        return new ResponseEntity<>(service.addUser(form), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/login")
    public ResponseEntity<Response<String>> login(@RequestBody LoginForm form, HttpServletResponse res) throws Exception {
        return new ResponseEntity<>(service.authenticate(form, res), HttpStatus.OK);
    }

    @Override
    @GetMapping("/get/info")
    public ResponseEntity<Response<UserInfo>> getInfo(@CookieValue String token) {
        return new ResponseEntity<>(service.getUserInfo(token), HttpStatus.OK);
    }

    /**
     * Methods below have not been implemented yet
     * coming soon!
     */
    @Override
    public ResponseEntity<Response<String>> updateUsername(@CookieValue String token, @RequestBody UpdateAccountForm form) {
        return null;
    }
    @Override
    @PutMapping("/update/password")
    public ResponseEntity<Response<String>> updatePassword(String token, UpdateUserPasswordForm form, HttpServletResponse res) {
        return new ResponseEntity<>(service.updatePassword(token, form, res), HttpStatus.OK);
    }

    @Override
    @PutMapping("/logout")
    public ResponseEntity<Response<String>> logout(String token, HttpServletResponse res) {
        return new ResponseEntity<>(service.logout(res), HttpStatus.OK);
    }


}
