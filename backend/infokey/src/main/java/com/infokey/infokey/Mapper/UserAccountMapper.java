package com.infokey.infokey.Mapper;

import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.DTO.UserAccount;
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
}
