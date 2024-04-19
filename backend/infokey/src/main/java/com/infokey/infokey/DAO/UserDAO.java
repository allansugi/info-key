package com.infokey.infokey.DAO;

import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.interfaces.DAO.IDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO implements IDAO<UserAccount> {

    private final JdbcClient jdbcClient;
    public UserDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(UserAccount item) throws SQLException {
        int update = jdbcClient.sql("INSERT INTO \"user\" (id, username, email, password) VALUES (?, ?, ?, ?)")
                .params(item.getId(), item.getUsername(), item.getEmail(), item.getPassword())
                .update();

        Assert.state(update == 1, "failed to add user " + item.getUsername());
    }

    @Override
    public void update(UserAccount item) throws SQLException {
        int update = jdbcClient.sql("UPDATE \"user\" SET email = ?, username = ?, password = ? WHERE id = ?")
                .params(item.getEmail(), item.getUsername(), item.getPassword(), item.getId())
                .update();

        Assert.state(update == 1, "failed to update user " + item.getId());
    }

    @Override
    public UserAccount findById(String id) throws SQLException {
        return jdbcClient.sql("SELECT * FROM \"user\" where id = ?")
                .params(id)
                .query(UserAccount.class)
                .single();
    }

    @Override
    public void delete(String id) throws SQLException {
        int update = jdbcClient.sql("DELETE FROM \"user\" where id = ?")
                .params(id)
                .update();

        Assert.state(update == 1, "failed to delete user " + id);
    }

    public List<UserAccount> findAll() throws SQLException {
            return jdbcClient.sql("SELECT * FROM \"user\"")
                    .query(UserAccount.class)
                    .list();
    }
}
