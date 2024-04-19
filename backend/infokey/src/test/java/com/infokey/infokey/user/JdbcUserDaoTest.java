package com.infokey.infokey.user;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(UserDAO.class)
public class JdbcUserDaoTest {

    @Autowired
    UserDAO dao;

    @BeforeEach
    void setup() throws SQLException {
        dao.save(new UserAccount(new RegisterForm("user1", "user1@gmail.com", "Password_1")));
        dao.save(new UserAccount(new RegisterForm("user2", "user2@gmail.com", "Password_2")));
        dao.save(new UserAccount(new RegisterForm("user3", "user3@gmail.com", "Password_3")));
    }

    @Test
    void shouldFindAllUsers() throws SQLException {
        List<UserAccount> userAccounts = dao.findAll();
        assertEquals(3, userAccounts.size());
    }

    @Test
    void shouldCreateNewUserAccount() throws SQLException {
        dao.save(new UserAccount(new RegisterForm("user4", "user4@gmail.com", "Password_4")));
        List<UserAccount> userAccounts = dao.findAll();
        assertEquals(4, userAccounts.size());
    }

    @Test
    void shouldUpdateUserAccountInfo() throws SQLException {
        UserAccount account = new UserAccount(new RegisterForm("user1", "user1@gmail.com", "Password_1"));
        String id = account.getId();

        dao.save(account);
        dao.update(new UserAccount("newuser1","newuser1@gmail.com", "newPassword_1", id), id);
        Optional<UserAccount> updatedAccount = dao.findById(id);
        assertTrue(updatedAccount.isPresent());
        UserAccount verifiedUpdatedAccount = updatedAccount.get();

        assertEquals(verifiedUpdatedAccount.getId(), id);
        assertEquals(verifiedUpdatedAccount.getEmail(), "newuser1@gmail.com");
        assertEquals(verifiedUpdatedAccount.getUsername(), "newuser1");
        assertEquals(verifiedUpdatedAccount.getPassword(), "newPassword_1");
    }

    @Test
    void shouldDeleteAnAccount() throws SQLException {
        UserAccount account = new UserAccount(new RegisterForm("user4", "user4@gmail.com", "Password_4"));
        String id = account.getId();

        dao.save(account);
        List<UserAccount> userAccounts = dao.findAll();
        assertEquals(4, userAccounts.size());

        dao.delete(id);
        List<UserAccount> updatedUserAccounts = dao.findAll();
        assertEquals(3, updatedUserAccounts.size());
    }

    @Test
    void wrongIdShouldNotFindAccount() {
        Optional<UserAccount> account = dao.findById("");
        assertTrue(account.isEmpty());
    }
}
