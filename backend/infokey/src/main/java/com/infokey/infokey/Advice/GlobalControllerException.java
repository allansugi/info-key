package com.infokey.infokey.Advice;

import com.infokey.infokey.Exceptions.IllegalRegisterException;
import com.infokey.infokey.Exceptions.LoginNotFoundException;
import com.infokey.infokey.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

public class GlobalControllerException {

    /**
     * bad client input (invalid password or email requirement / parameter)
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalRegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response<String>> handleBadRequestException(IllegalRegisterException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error on database side (or other external services)
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Response<String>> handleDatabaseErrorException(Exception ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Error for invalid credential
     * @param ex
     * @return
     */
    @ExceptionHandler(LoginNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<Response<String>> handleAuthenticationException(LoginNotFoundException ex) {
        Response<String> response = new Response<>();
        response.setSuccess(false);
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
