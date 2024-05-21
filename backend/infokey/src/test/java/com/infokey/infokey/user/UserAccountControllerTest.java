package com.infokey.infokey.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infokey.infokey.Controllers.UserAccountController;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Exceptions.UserAccount.UserAccountNotFoundException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateUserPasswordForm;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.UserAccountService;
import com.infokey.infokey.ViewModel.UserInfo;
import jakarta.servlet.http.Cookie;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void shouldFindUserInfoWithCorrectResponseCode() throws Exception {
        String token = "validToken";
        Cookie cookie= new Cookie("token", token);
        Response<UserInfo> successfulResponse = new Response<>(true, new UserInfo("user", "email"));
        when(service.getUserInfo(token)).thenReturn(successfulResponse);
        mockMvc.perform(get("/api/user/get/info").cookie(cookie)).andExpect(status().isOk());
    }

    @Test
    void shouldNotFindUserInfoWithErrorResponseCode() throws Exception {
        String token = "validToken";
        Cookie cookie= new Cookie("token", token);
        when(service.getUserInfo(token)).thenThrow(UserAccountNotFoundException.class);
        mockMvc.perform(get("/api/user/get/info").cookie(cookie)).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePasswordResponseCodeOk() throws Exception {
        String token = "validToken";
        Cookie cookie = new Cookie("token", token);
        Response<String> response = new Response<>(true, "success");
        UpdateUserPasswordForm form = mock(UpdateUserPasswordForm.class);
        when(service.updatePassword(any(String.class), any(UpdateUserPasswordForm.class), any(HttpServletResponse.class)))
                .thenReturn(response);
        mockMvc.perform(put("/api/user/update/password")
                    .cookie(cookie)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdatePasswordResponseCodeNotFound() throws Exception {
        String token = "InvalidToken";
        Cookie cookie = new Cookie("token", token);
        UpdateUserPasswordForm form = mock(UpdateUserPasswordForm.class);
        when(service.updatePassword(any(String.class), any(UpdateUserPasswordForm.class), any(HttpServletResponse.class)))
                .thenThrow(UserAccountNotFoundException.class);
        mockMvc.perform(put("/api/user/update/password")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePasswordResponseCodeUnauthorized() throws Exception {
        String token = "validToken";
        Cookie cookie = new Cookie("token", token);
        UpdateUserPasswordForm form = mock(UpdateUserPasswordForm.class);
        when(service.updatePassword(any(String.class), any(UpdateUserPasswordForm.class), any(HttpServletResponse.class)))
                .thenThrow(UnauthorizedCredentialException.class);
        mockMvc.perform(put("/api/user/update/password")
                    .cookie(cookie)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isUnauthorized());
    }

}
