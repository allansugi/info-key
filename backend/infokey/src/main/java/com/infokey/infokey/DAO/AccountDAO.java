package com.infokey.infokey.DAO;

import com.infokey.infokey.Model.Account;
import com.infokey.infokey.ViewModel.AccountViewModel;
import com.infokey.infokey.interfaces.DAO.IDAO;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDAO implements IDAO<Account> {

    JdbcClient jdbcClient;
    public AccountDAO(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void save(Account item) throws SQLException {
        jdbcClient.sql("INSERT INTO account (id, userId, account_name, account_username, account_password) VALUES (?, ?, ?, ?, ?)")
                .params(item.getId(), item.getUserId(), item.getAccount_name(), item.getAccount_username(), item.getAccount_password())
                .update();
    }

    @Override
    public void update(Account item) throws SQLException {
        jdbcClient.sql(
                    """
                    UPDATE account
                    SET account_name = ?,
                        account_username = ?,
                        account_password = ?
                    WHERE id = ? AND userId = ?
                    """)
                .params(item.getAccount_name(), item.getAccount_username(), item.getAccount_password(), item.getId(), item.getUserId())
                .update();
    }

    @Override
    public Account findById(String id) throws SQLException {
        return jdbcClient.sql("SELECT * FROM account where id = ?")
                .params(id)
                .query(Account.class)
                .single();
    }

    @Override
    public void delete(String id) throws SQLException {
        jdbcClient.sql("DELETE FROM account where id = ?")
                .params(id)
                .update();
    }

    public List<AccountViewModel> findByUserId(String userId) throws SQLException {
        return jdbcClient.sql("SELECT * FROM account where userId = ?")
                .params(userId)
                .query(AccountViewModel.class)
                .list();
    }
    
}
