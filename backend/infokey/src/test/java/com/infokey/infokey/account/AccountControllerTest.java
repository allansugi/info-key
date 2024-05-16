package com.infokey.infokey.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infokey.infokey.Controllers.AccountController;
import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.AccountService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService service;

    private final List<Account> accounts = new ArrayList<>();
    private Cookie cookie;
    private String token;
    private String id;

    @BeforeEach
    void setup() {
        String id = UUID.randomUUID().toString();

        this.id = id;
        this.token = "TestToken";
        this.cookie = new Cookie("token", token);

        accounts.add(new Account(id,
                UUID.randomUUID().toString(),
                        "name",
                        "username",
                        "password"));
    }

    @Test
    void shouldCreateNewAccount() throws Exception {
        AccountForm form = new AccountForm("account", "username", "password");
        when(service.addAccount(token, form)).thenReturn(new Response<String>(true, "Account added"));
        mockMvc.perform(post("/api/user/account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
                        .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAccount() throws Exception {
        Response<List<Account>> successfulResponse = new Response<>(true, accounts);
        when(service.findUserAccounts(token)).thenReturn(successfulResponse);
        mockMvc.perform(get("/api/user/account/find/accounts").cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.size()").value(accounts.size()));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        Cookie cookie = new Cookie("token", token);
        Response<String> succcessfulResponse = new Response<>(true, "account information updated");
        AccountForm form = new AccountForm("name", "newusername", "newpassword");
        when(service.updateAccount(token, form, id)).thenReturn(succcessfulResponse);
        mockMvc.perform(put("/api/user/account/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(form))
                        .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        Cookie cookie = new Cookie("token", token);
        Response<String> succcessfulResponse = new Response<>(true, "account deleted");
        mockMvc.perform(delete("/api/user/account/delete/" + id)
                        .cookie(cookie))
                .andExpect(status().isOk());
    }

}
