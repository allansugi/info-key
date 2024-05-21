package com.infokey.infokey.interfaces.DAO;

import com.infokey.infokey.DTO.UserAccount;

import java.util.List;

public interface IUserDAO {
    List<UserAccount> findAll();
    int updatePassword(String newPassword, String id);
}
