package com.infokey.infokey.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infokey.infokey.Controllers.AccountController;
import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.AccountService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService service;

    @Test
    void shouldCreateNewAccount() throws Exception {
        String token = "TestToken";
        Cookie cookie = new Cookie("token", token);
        AccountForm form = new AccountForm("account", "username", "password");
        when(service.addAccount(token, form)).thenReturn(new Response<String>(true, "Account added"));
        mockMvc.perform(post("/api/account/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
                        .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAccount() throws Exception {

    }

    @Test
    void shouldUpdateAccount() throws Exception {

    }

    @Test
    void shouldDeleteAccount() throws Exception {

    }

}
