package com.infokey.infokey.Mapper;

import com.infokey.infokey.Form.AccountForm;
import com.infokey.infokey.DTO.Account;

import java.util.UUID;

public class AccountMapper {

    public AccountMapper() {
        // Default Constructor
    }

    public Account toDTO(AccountForm form, String userId) {
        return new Account(UUID.randomUUID().toString(),
                            userId,
                            form.getAccountName(),
                            form.getAccountUsername(),
                            form.getAccountPassword());
    }
}
