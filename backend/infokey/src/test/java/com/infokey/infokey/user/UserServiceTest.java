package com.infokey.infokey.user;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.Exceptions.IllegalRegisterException;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.Response;
import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.Services.UserAccountService;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO dao;

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
    }

    @Test
    void shouldGetSuccessfulResponseWithValidPassword() {
        RegisterForm register = new RegisterForm("username2",
                "username2@email.com",
                "ValidPassword_1");

        Response<String> response = service.addUser(register);
        assertTrue(response.getSuccess(), "invalid requirement");
        assertEquals(response.getResponse(), "Registration successful");
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoUppercase() {
        RegisterForm register = new RegisterForm("username2",
                "username2@email.com",
                "validpassword_1");

        Exception exception = assertThrows(IllegalRegisterException.class, () -> service.addUser(register));
        String exceptionMessage = exception.getMessage();
        String expectedMessage = "Password does not meet the requirement";

        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoLowercase() {
        RegisterForm register = new RegisterForm("username2",
                "username2@email.com",
                "VALIDPASSWORD_1");

        Exception exception = assertThrows(IllegalRegisterException.class, () -> service.addUser(register));
        String exceptionMessage = exception.getMessage();
        String expectedMessage = "Password does not meet the requirement";

        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoDigit() {
        RegisterForm register = new RegisterForm("username2",
                "username2@email.com",
                "ValidPassword_p");

        Exception exception = assertThrows(IllegalRegisterException.class, () -> service.addUser(register));
        String exceptionMessage = exception.getMessage();
        String expectedMessage = "Password does not meet the requirement";

        assertTrue(exceptionMessage.contains(expectedMessage));
    }

    @Test
    void shouldGetIllegalRegisterExceptionWithNoSpecialCharacter() {
        RegisterForm register = new RegisterForm("username2",
                "username2@email.com",
                "ValidPassword1");

        Exception exception = assertThrows(IllegalRegisterException.class, () -> service.addUser(register));
        String exceptionMessage = exception.getMessage();
        String expectedMessage = "Password does not meet the requirement";

        assertTrue(exceptionMessage.contains(expectedMessage));
    }
}
