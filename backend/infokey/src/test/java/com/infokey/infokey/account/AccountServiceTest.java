package com.infokey.infokey.account;

import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Mapper.AccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.AccountService;
import com.infokey.infokey.Util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountDAO dao;
    @Mock
    JWTUtil jwt;
    @Mock
    AccountMapper mapper;
    @InjectMocks
    AccountService service;

    private final List<Account> userAccounts = new ArrayList<>();

    @Test
    void shouldCreateNewAccount() {
        String userId = UUID.randomUUID().toString();
        String token = "validToken1";
        AccountForm form = new AccountForm("accountname", "username", "password");
        when(jwt.verifyToken(token)).thenReturn(userId);
        Response<String> response = service.addAccount(token, form);

        assertTrue(response.getSuccess(), "token not validated or account not valid");
    }

    @Test
    void shouldFindAccount() {
        String userId = UUID.randomUUID().toString();
        Account account = new Account(UUID.randomUUID().toString(), userId, "accountname", "username", "password");
        String token = "validToken1";
    }

    @Test
    void shouldDeleteAccount() {

    }

    @Test
    void shouldUpdateAccount() {

    }

    // for invalid id

}
