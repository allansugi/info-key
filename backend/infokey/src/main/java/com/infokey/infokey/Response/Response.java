package com.infokey.infokey.Response;
/**
 * Response Body
 */
public class Response<T> {
    private boolean success;
    private T response;

    public Response(boolean success, T response) {
        this.success = success;
        this.response = response;
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
