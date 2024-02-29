package com.infokey.infokey.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infokey.infokey.DB.DBUtil;
import com.infokey.infokey.Model.Account;
import com.infokey.infokey.interfaces.DAO.IDAO;

public class AccountDAO implements IDAO<Account> {

    @Autowired
    public AccountDAO() {
        // Default Constructor        
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

            stmt.setString(0, item.getId());
            stmt.setString(1, item.getUserId());
            stmt.setString(2, item.getAccount_name());
            stmt.setString(3, item.getAccount_username());
            stmt.setString(4, item.getAccount_password());

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

            stmt.setString(0, item.getId());
            stmt.setString(1, item.getUserId());
            stmt.setString(2, item.getAccount_name());
            stmt.setString(3, item.getAccount_username());
            stmt.setString(4, item.getAccount_password());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM account");

            while(rs.next()) {
                Account account = new Account();
                
                String id = rs.getString("id");
                String userId = rs.getString("userId");
                String accountName = rs.getString("account_name");
                String accountUsername = rs.getString("account_username");
                String accountPassword = rs.getString("account_password");

                account.setId(id);
                account.setUserId(userId);
                account.setAccount_username(accountUsername);
                account.setAccount_name(accountName);
                account.setAccount_password(accountPassword);

                accounts.add(account);
            }

            return accounts;
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
    
}
