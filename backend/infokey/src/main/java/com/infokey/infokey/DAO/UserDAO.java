package com.infokey.infokey.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.Util.DBUtil;
import com.infokey.infokey.interfaces.DAO.IDAO;

public class UserDAO implements IDAO<UserAccount> {

    @Autowired
    public UserDAO() {
        
    }

    @Override
    public void save(UserAccount item) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                """
                INSERT INTO account (id, userId, account_name, account_username, account_password) 
                VALUES (?, ?, ?, ?, ?)
                """
            );

            stmt.setString(0, item.getId());
            stmt.setString(1, item.getEmail());
            stmt.setString(2, item.getUsername());
            stmt.setString(3, item.getPassword());

            stmt.execute();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public void update(UserAccount item) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                """
                UPDATE user
                SET email = ?,
                    username = ?,
                    password = ?
                WHERE id = ?
                """
            );

            stmt.setString(0, item.getId());
            stmt.setString(1, item.getEmail());
            stmt.setString(2, item.getUsername());
            stmt.setString(3, item.getPassword());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public List<UserAccount> findAll() throws SQLException {
         List<UserAccount> accounts = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while(rs.next()) {
                UserAccount account = new UserAccount();
                
                String id = rs.getString("id");
                String email= rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");

                account.setId(id);
                account.setEmail(email);
                account.setUsername(username);
                account.setPassword(password);

                accounts.add(account);
            }

            return accounts;
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }

    @Override
    public UserAccount findById(String id) throws SQLException {
        try (Connection  conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user where id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserAccount account = new UserAccount();
                
                String email= rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");

                account.setId(id);
                account.setEmail(email);
                account.setUsername(username);
                account.setPassword(password);

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
            PreparedStatement statement = conn.prepareStatement("DELETE FROM user where id = ?");
            statement.setString(1, id);
            statement.execute();
        } catch (Exception e) {
            throw new SQLException("Database connection error");
        }
    }
    
}
