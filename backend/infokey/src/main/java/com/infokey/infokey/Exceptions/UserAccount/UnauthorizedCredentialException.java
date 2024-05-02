package com.infokey.infokey.Exceptions.UserAccount;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedCredentialException extends RuntimeException {
    public UnauthorizedCredentialException() {}
    public UnauthorizedCredentialException(String message) {
        super(message);
    }
}
