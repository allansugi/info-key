package com.infokey.infokey.interfaces.Controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.Response;

public interface IAccountController {
    ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, AccountForm form);

    ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody Account account) throws SQLException;

    ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId) throws SQLException;

    ResponseEntity<Response<List<Account>>> findUserAccounts(@CookieValue String token) throws SQLException;
}