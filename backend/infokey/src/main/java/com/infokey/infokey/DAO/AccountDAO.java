package com.infokey.infokey.DAO;

import com.infokey.infokey.Model.Account;
import com.infokey.infokey.Util.DBUtil;
import com.infokey.infokey.ViewModel.AccountViewModel;
import com.infokey.infokey.interfaces.DAO.IDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDAO implements IDAO<Account> {

    JdbcClient jdbcClient;
    public AccountDAO(JdbcClient jdbcClient) {
        // Default Constructor
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Account item) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                """
                INSERT INTO account (id, userId, account_name, account_username, account_password)
                VALUES (?, ?, ?, ?, ?)
                """
            );

            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUserId());
            stmt.setString(3, item.getAccount_name());
            stmt.setString(4, item.getAccount_username());
            stmt.setString(5, item.getAccount_password());

            stmt.execute();

        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public void update(Account item) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                """
                UPDATE account
                SET account_name = ?,
                    account_username = ?,
                    account_password = ?
                WHERE id = ?
                AND userId = ?
                """
            );

            stmt.setString(1, item.getAccount_name());
            stmt.setString(2, item.getAccount_username());
            stmt.setString(3, item.getAccount_password());
            stmt.setString(4, item.getId());
            stmt.setString(5, item.getUserId());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public Account findById(String id) throws SQLException {
        try (Connection  conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account where id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                
                String userId = rs.getString("userId");
                String accountName = rs.getString("account_name");
                String accountUsername = rs.getString("account_username");
                String accountPassword = rs.getString("account_password");

                account.setId(id);
                account.setUserId(userId);
                account.setAccount_username(accountUsername);
                account.setAccount_name(accountName);
                account.setAccount_password(accountPassword);

                return account;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM account where id = ?");
            statement.setString(1, id);
            statement.execute();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    public List<AccountViewModel> findByUserId(String userId) throws SQLException {
        List<AccountViewModel> accounts = new ArrayList<>();
        try (Connection  conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account where userId = ?");
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountViewModel account = new AccountViewModel();

                String id = rs.getString("id");
                String accountName = rs.getString("account_name");
                String accountUsername = rs.getString("account_username");
                String accountPassword = rs.getString("account_password");

                account.setId(id);
                account.setAccountUsername(accountUsername);
                account.setAccountName(accountName);
                account.setAccountPassword(accountPassword);

                accounts.add(account);
            }

            return accounts;
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }
    
}
