package com.infokey.infokey.DAO;

import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.interfaces.DAO.IDAO;
import com.infokey.infokey.interfaces.DAO.IUserDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IDAO<UserAccount>, IUserDAO {

    private final JdbcClient jdbcClient;
    public UserDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public int save(UserAccount item) {
        return jdbcClient.sql("INSERT INTO user_account (id, username, email, password) VALUES (?, ?, ?, ?)")
                .params(item.getId(), item.getUsername(), item.getEmail(), item.getPassword())
                .update();
    }

    @Override
    public int update(UserAccount item, String id) {
        return jdbcClient.sql("UPDATE user_account SET email = ?, username = ?, password = ? WHERE id = ?")
                .params(item.getEmail(), item.getUsername(), item.getPassword(), item.getId())
                .update();
    }

    @Override
    public Optional<UserAccount> findById(String id) {
        return jdbcClient.sql("SELECT * FROM user_account where id = ?")
                .params(id)
                .query(UserAccount.class)
                .optional();
    }

    @Override
    public int delete(String id) {
        return jdbcClient.sql("DELETE FROM user_account where id = ?")
                .params(id)
                .update();
    }

    @Override
    public List<UserAccount> findAll() {
            return jdbcClient.sql("SELECT * FROM user_account")
                    .query(UserAccount.class)
                    .list();
    }

    @Override
    public int updatePassword(String newPassword, String id) {
        return jdbcClient.sql("UPDATE user_account SET password = ? where id = ?")
                .params(newPassword, id)
                .update();
    }
}
