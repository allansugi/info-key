package com.infokey.infokey.DAO;

import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.Util.DBUtil;
import com.infokey.infokey.interfaces.DAO.IDAO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO implements IDAO<UserAccount> {

    public UserDAO() {
        
    }

    @Override
    public void save(UserAccount item) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                """
                INSERT INTO user (id, username, email, password)
                VALUES (?, ?, ?, ?)
                """
            );

            stmt.setString(1, item.getId());
            stmt.setString(2, item.getUsername());
            stmt.setString(3, item.getEmail());
            stmt.setString(4, item.getPassword());

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

            stmt.setString(1, item.getId());
            stmt.setString(2, item.getEmail());
            stmt.setString(3, item.getUsername());
            stmt.setString(4, item.getPassword());

            stmt.executeUpdate();
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
}
