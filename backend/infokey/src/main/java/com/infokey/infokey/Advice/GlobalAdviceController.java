package com.infokey.infokey.Advice;

import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalAdviceController {
    @ExceptionHandler(PasswordRequirementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Response<String> handleInvalidPasswordRequirements() {
        return new Response<>(false, "password does not meet the requirement");
    }

    @ExceptionHandler(UnauthorizedCredentialException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    Response<String> handleFailedLogin() {
        return new Response<>(false, "incorrect credentials");
    }
}
