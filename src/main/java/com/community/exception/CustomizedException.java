package com.community.exception;

public class CustomizedException extends RuntimeException {

    private String message;

    public CustomizedException(ICustomizedErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
