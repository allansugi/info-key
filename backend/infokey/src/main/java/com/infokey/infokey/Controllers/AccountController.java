package com.infokey.infokey.Controllers;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.AccountService;
import com.infokey.infokey.interfaces.Controller.IAccountController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/user/account")
public class AccountController implements IAccountController {
    private final AccountService service;
    public AccountController(AccountService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, @RequestBody AccountForm account) {
        Response<String> response = this.service.addAccount(token, account);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PutMapping("/update/{accountId}")
    public ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody AccountForm account, @PathVariable String accountId) {
        Response<String> response = this.service.updateAccount(token, account, accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId) {
        Response<String> response = this.service.deleteAccount(token, accountId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @GetMapping("/find/accounts")
    public ResponseEntity<Response<List<Account>>> findUserAccounts(@CookieValue String token) {
        Response<List<Account>> response= this.service.findUserAccounts(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
