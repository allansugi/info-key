package com.infokey.infokey.DAO;

import com.infokey.infokey.DTO.Account;
import com.infokey.infokey.interfaces.DAO.IDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAO implements IDAO<Account> {

    private final JdbcClient jdbcClient;
    public AccountDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public int save(Account item) {
        return jdbcClient.sql("INSERT INTO vault_account (id, userId, account_name, account_username, account_password) VALUES (?, ?, ?, ?, ?)")
                .params(item.getId(), item.getUserId(), item.getAccount_name(), item.getAccount_username(), item.getAccount_password())
                .update();
    }

    @Override
    public int update(Account item, String id) {
        return jdbcClient.sql(
                    """
                    UPDATE vault_account
                    SET account_name = ?,
                        account_username = ?,
                        account_password = ?
                    WHERE id = ? AND userId = ?
                    """)
                .params(item.getAccount_name(), item.getAccount_username(), item.getAccount_password(), id, item.getUserId())
                .update();
    }

    @Override
    public Optional<Account> findById(String id) {
        return jdbcClient.sql("SELECT * FROM vault_account where id = ?")
                .params(id)
                .query(Account.class)
                .optional();
    }

    @Override
    public int delete(String id) {
        return jdbcClient.sql("DELETE FROM vault_account where id = ?")
                .params(id)
                .update();
    }

    public List<Account> findByUserId(String userId) {
        return jdbcClient.sql("SELECT * FROM vault_account where userId = ?")
                .params(userId)
                .query(Account.class)
                .list();
    }
    
}
