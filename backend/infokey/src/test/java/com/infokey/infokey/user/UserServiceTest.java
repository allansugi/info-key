package com.infokey.infokey.user;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Mapper.UserAccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Services.UserAccountService;
import com.infokey.infokey.Util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setup() {
        userAccounts.add(
                new UserAccount("username1",
                        "username1@email.com",
                        "Password_1", UUID.randomUUID().toString())
        );

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
//        Response<String> response = service.addUser(form);
//        assertFalse(response.getSuccess(), "get success should be false");
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
}
