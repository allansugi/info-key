package com.infokey.infokey.account;

import com.infokey.infokey.DAO.AccountDAO;
import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.ViewModel.AccountViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import({AccountDAO.class,UserDAO.class})
public class JdbcAccountDaoTest {
    AccountDAO accountDao;
    UserDAO userDao;
    String userId;

    @Autowired
    public JdbcAccountDaoTest(AccountDAO accountDao, UserDAO userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.userId = UUID.randomUUID().toString();
    }

    @BeforeEach
    void setup() {
        this.userDao.save(new UserAccount("user1", "user1@gmail.com", "Password_1", this.userId));
    }

    @Test
    void shouldAddNewAccount() {
        this.accountDao.save(new Account(UUID.randomUUID().toString(), userId, "account4", "account_username4", "accountpassword4"));
        List<AccountViewModel> accounts = this.accountDao.findByUserId(this.userId);
        assertEquals(accounts.size(), 1);
    }

    @Test
    void shouldFindAllAccountsByUserId() {
        this.accountDao.save(new Account(UUID.randomUUID().toString(), userId, "account1", "account_username1", "accountpassword1"));
        this.accountDao.save(new Account(UUID.randomUUID().toString(), userId, "account2", "account_username2", "accountpassword2"));
        List<AccountViewModel> accounts = this.accountDao.findByUserId(this.userId);
        assertEquals(accounts.size(), 2);
    }

    @Test
    void shouldNotFindAccountByDifferentUserId() {
        this.accountDao.save(new Account(UUID.randomUUID().toString(), userId, "account1", "account_username1", "accountpassword1"));
        this.accountDao.save(new Account(UUID.randomUUID().toString(), userId, "account2", "account_username2", "accountpassword2"));
        List<AccountViewModel> accounts = this.accountDao.findByUserId("");
        assertEquals(accounts.size(), 0);
    }

    @Test
    void shouldFindAccountById() {
        String accountId = UUID.randomUUID().toString();
        this.accountDao.save(new Account(accountId, userId, "account1", "account_username1", "accountpassword1"));
        Optional<Account> account = this.accountDao.findById(accountId);
        assertTrue(account.isPresent());
    }

    @Test
    void shouldNotFindAccountByInvalidId() {
        String accountId = UUID.randomUUID().toString();
        this.accountDao.save(new Account(accountId, userId, "account1", "account_username1", "accountpassword1"));
        Optional<Account> account = this.accountDao.findById("");
        assertTrue(account.isEmpty());
    }

    @Test
    void shouldDeleteAccount() {
        String accountId = UUID.randomUUID().toString();
        this.accountDao.save(new Account(accountId, userId, "account4", "account_username4", "accountpassword4"));
        List<AccountViewModel> accounts = this.accountDao.findByUserId(this.userId);
        assertEquals(accounts.size(), 1);

        this.accountDao.delete(accountId);
        List<AccountViewModel> updatedAccounts = this.accountDao.findByUserId(this.userId);
        assertEquals(updatedAccounts.size(), 0);
    }

    @Test
    void shouldUpdateAccount() {
        String accountId = UUID.randomUUID().toString();
        this.accountDao.save(new Account(accountId, userId, "account4", "account_username4", "accountpassword4"));
        this.accountDao.update(new Account(accountId, userId, "newaccount4", "newaccount_username4", "newaccountpassword4"),accountId);
        Optional<Account> account = this.accountDao.findById(accountId);
        assertTrue(account.isPresent());
        Account getAccount = account.get();

        assertEquals(getAccount.getAccount_name(), "newaccount4");
        assertEquals(getAccount.getAccount_username(), "newaccount_username4");
        assertEquals(getAccount.getAccount_password(), "newaccountpassword4");
    }
}
