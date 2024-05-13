package com.infokey.infokey.Exceptions.UserAccount;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordRequirementException extends RuntimeException {
    public PasswordRequirementException() {

    }
    public PasswordRequirementException(String message) {
        super(message);
    }
}
