package com.infokey.infokey.user;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Exceptions.UserAccount.UserAccountNotFoundException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateUserPasswordForm;
import com.infokey.infokey.Mapper.UserAccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.UserAccountService;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.ViewModel.UserInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO dao;
    @Mock
    UserAccountMapper mapper;
    @Mock
    JWTUtil util;
    @InjectMocks
    UserAccountService service;

    private final List<UserAccount> userAccounts = new ArrayList<>();
    private final String userId = UUID.randomUUID().toString();
    private final UserAccount account = new UserAccount(
                                        "username1",
                                        "username1@email.com",
                                        "Password_1",
                                                userId);

    @BeforeEach
    void setup() {
        userAccounts.add(account);

        userAccounts.add(
                new UserAccount("username2",
                        "username2@email.com",
                        "Password_2", UUID.randomUUID().toString())
        );
    }

    @Test
    void shouldGetSuccessfulResponseRegisterWithValidPassword() {

        RegisterForm form = new RegisterForm("username2", "username2@email.com", "ValidPassword_1");
        when(mapper.toDTO(form)).thenReturn(new UserAccount("username2",
                                                        "username2@email.com",
                                                        "ValidPassword_1",
                                                        UUID.randomUUID().toString()));
        Response<String> response = service.addUser(form);
        assertTrue(response.getSuccess(), "invalid requirement");
        assertEquals(response.getResponse(), "Registration successful");
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoUppercase() {
        RegisterForm form = new RegisterForm("username2", "username2@email.com", "validpassword_1");
        assertThrows(PasswordRequirementException.class, () -> service.addUser(form));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoLowercase() {
        RegisterForm form = new RegisterForm("username2", "username2@email.com", "VALIDPASSWORD_1");
        assertThrows(PasswordRequirementException.class, () -> service.addUser(form));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoDigit() {
        RegisterForm form = new RegisterForm("username2", "username2@email.com", "ValidPassword&");
        assertThrows(PasswordRequirementException.class, () -> service.addUser(form));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoSpecialCharacter() {
        RegisterForm form = new RegisterForm("username2", "username2@email.com", "ValidPassword1");
        assertThrows(PasswordRequirementException.class, () -> service.addUser(form));
    }

    @Test
    void shouldLoggedInWithValidEmailLoginDetails() {
        String user = "username2@email.com";
        String password = "Password_2";
        LoginForm form = new LoginForm(user, password);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(dao.findAll()).thenReturn(userAccounts);
        Response<String> response = service.authenticate(form, res);

        assertTrue(response.getSuccess(), "invalid credentials");
    }

    @Test
    void shouldLoggedInWithValidUsernameLoginDetails() {
        String user = "username2";
        String password = "Password_2";
        LoginForm form = new LoginForm(user, password);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(dao.findAll()).thenReturn(userAccounts);
        Response<String> response = service.authenticate(form, res);

        // only test success attribute
        // because response attribute would always be string token
        assertTrue(response.getSuccess(), "invalid credentials");
    }

    @Test
    void shouldThrowErrorWithInValidUserLoginDetails() {
        String user = "username--2";
        String password = "Password_2";
        LoginForm form = new LoginForm(user, password);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(dao.findAll()).thenReturn(userAccounts);

        assertThrows(UnauthorizedCredentialException.class, () -> service.authenticate(form, res));
    }

    @Test
    void shouldThrowErrorWithInValidPasswordLoginDetails() {
        String user = "username2";
        String password = "Password_1";
        LoginForm form = new LoginForm(user, password);
        HttpServletResponse res = mock(HttpServletResponse.class);

        when(dao.findAll()).thenReturn(userAccounts);

        assertThrows(UnauthorizedCredentialException.class, () -> service.authenticate(form, res));
    }

    @Test
    void shouldFindUserInfo() {
        String token = "validToken1";
        UserInfo userInfo = mapper.toUserInfo(account);
        when(util.verifyToken(token)).thenReturn(this.userId);
        when(dao.findById(any(String.class))).thenReturn(Optional.of(account));
        when(mapper.toUserInfo(account)).thenReturn(userInfo);

        Response<UserInfo> response = service.getUserInfo(token);
        assertTrue(response.getSuccess());
        assertEquals(response.getResponse(), userInfo);
    }

    @Test
    void shouldNotFindUserInfoAndThrowError() {
        String token = "invalidToken1";
        when(util.verifyToken(token)).thenReturn(UUID.randomUUID().toString());
        assertThrows(UserAccountNotFoundException.class, () -> service.getUserInfo(token));
    }

    @Test
    void shouldUpdateUserPassword() {
        String token = "validToken";
        when(util.verifyToken(token)).thenReturn(userId);
        when(dao.findById(userId)).thenReturn(Optional.of(account));
        HttpServletResponse res = mock(HttpServletResponse.class);

        UpdateUserPasswordForm form = new UpdateUserPasswordForm("Password_1", "new_Password123");
        when(dao.updatePassword(form.getNewPassword(), userId)).thenReturn(1);
        Response<String> response = service.updatePassword(token, form, res);
        assertTrue(response.getSuccess());
    }

    @Test
    void shouldNotUpdateUserPasswordWithInvalidIdNotFound() {
        String token = "validToken";

        // new Id not associated with any account
        String id = UUID.randomUUID().toString();
        when(util.verifyToken(token)).thenReturn(id);

        String newPassword = "New_Password_123";
        UpdateUserPasswordForm form = new UpdateUserPasswordForm("Password_1", newPassword);
        HttpServletResponse res = mock(HttpServletResponse.class);

        assertThrows(UserAccountNotFoundException.class, () -> service.updatePassword(token, form, res));
    }

    @Test
    void shouldNotUpdateUserPasswordWithValidIdWrongMasterPassword() {
        String token = "validToken";
        when(util.verifyToken(token)).thenReturn(userId);
        when(dao.findById(userId)).thenReturn(Optional.of(account));
        HttpServletResponse res = mock(HttpServletResponse.class);

        UpdateUserPasswordForm form = new UpdateUserPasswordForm("wrongpassword", "new_Password123");

        assertThrows(UnauthorizedCredentialException.class, () -> service.updatePassword(token, form, res));
    }
}
