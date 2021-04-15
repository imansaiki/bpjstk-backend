package com.bpjsk.monitor.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    private String message;
    private HttpStatus httpStatus;
    private Exception originalException;

    public CustomException(String message,HttpStatus httpStatus){
        this.message = message;
        this.httpStatus=httpStatus;
    }
    public CustomException(String message,HttpStatus httpStatus,Exception originalException){
        this.message = message;
        this.httpStatus=httpStatus;
        this.originalException=originalException;

    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Exception getOriginalException() {
        return originalException;
    }
}
