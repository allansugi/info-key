package com.infokey.infokey.Mapper;

import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.ViewModel.UserInfo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAccountMapper {
    public UserAccountMapper() {
        // Default constructor
    }

    public UserAccount toDTO(RegisterForm form) {
        return new UserAccount(form.getUsername(), form.getEmail(), form.getPassword(), UUID.randomUUID().toString());
    }

    public UserInfo toUserInfo(UserAccount account) {
        return new UserInfo(account.getUsername(), account.getEmail());
    }
}
