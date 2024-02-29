package com.infokey.infokey.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;

public interface AccountController {
    @PostMapping("/add")
    ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, AccountForm form);

    @PutMapping("/update")
    ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody Account account);

    @DeleteMapping("/delete/{accountId}")
    ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId);

    @GetMapping("/find/account")
    ResponseEntity<Response<List<Account>>> findUserAccounts(@CookieValue String token);
}