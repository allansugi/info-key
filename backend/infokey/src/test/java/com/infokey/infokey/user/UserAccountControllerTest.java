package com.infokey.infokey.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infokey.infokey.Controllers.UserAccountController;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.UserAccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserAccountService service;

    @Test
    void shouldCreateNewUserAccount() throws Exception {
        RegisterForm form = new RegisterForm("username", "email", "Password_1");
        when(service.addUser(form)).thenReturn(new Response<>(true, "success"));
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldLoginWithValidCredential() throws Exception {
        LoginForm form = new LoginForm("username", "Password_1");
        HttpServletResponse res = mock(HttpServletResponse.class);
        when(service.authenticate(form, res)).thenReturn(new Response<>(true, "successful login"));
        mockMvc.perform(put("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateUserAccountThrowError() throws Exception {
        RegisterForm form = new RegisterForm("username", "email", "Password_1");
        when(service.addUser(any(RegisterForm.class))).thenThrow(new PasswordRequirementException());
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotLogInWithInvalidCredential() throws Exception {
        LoginForm form = new LoginForm("username", "password");
        when(service.authenticate(any(LoginForm.class), any(HttpServletResponse.class))).thenThrow(UnauthorizedCredentialException.class);
        mockMvc.perform(put("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isUnauthorized());
    }
}
