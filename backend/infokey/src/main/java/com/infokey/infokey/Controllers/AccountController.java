package com.infokey.infokey.Controllers;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Form.UpdateAccountForm;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Services.AccountService;
import com.infokey.infokey.ViewModel.AccountViewModel;
import com.infokey.infokey.interfaces.Controller.IAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/account")
public class AccountController implements IAccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, @RequestBody AccountForm form) {
        System.out.println(form.toString());
        Response<String> response = this.service.addAccount(token, form);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody UpdateAccountForm account) throws SQLException {
        System.out.println(token);
        System.out.println(account.toString());
        Response<String> response = this.service.updateAccount(token, account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, String accountId) throws SQLException {
        Response<String> response = this.service.deleteAccount(token, accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @GetMapping("/find/accounts")
    public ResponseEntity<Response<List<AccountViewModel>>> findUserAccounts(@CookieValue String token) throws SQLException {
        System.out.println("token: " + token);
        Response<List<AccountViewModel>> response= this.service.findUserAccounts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
