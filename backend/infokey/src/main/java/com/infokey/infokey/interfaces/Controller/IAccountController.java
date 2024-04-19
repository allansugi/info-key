package com.infokey.infokey.interfaces.Controller;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.ViewModel.AccountViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

public interface IAccountController {
    ResponseEntity<Response<String>> insertNewAccount(@CookieValue String token, AccountForm form);

    ResponseEntity<Response<String>> updateExistingAccount(@CookieValue String token, @RequestBody AccountForm account, @PathVariable String id) throws SQLException;

    ResponseEntity<Response<String>> deleteAccount(@CookieValue String token, @PathVariable String accountId) throws SQLException;

    ResponseEntity<Response<List<AccountViewModel>>> findUserAccounts(@CookieValue String token) throws SQLException;
}