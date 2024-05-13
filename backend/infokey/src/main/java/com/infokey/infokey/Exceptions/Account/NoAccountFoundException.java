package com.infokey.infokey.Exceptions.Account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAccountFoundException extends RuntimeException {
    public NoAccountFoundException() {}
    public NoAccountFoundException(String message) {
        super(message);
    }
}
