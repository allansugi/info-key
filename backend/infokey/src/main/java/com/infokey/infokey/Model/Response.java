package com.infokey.infokey.Model;

public class Response<T> {
    private Boolean success;
    private T response;

    /**
     * Response Body
     */
    public Response() {
        // Default constructor
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
