package com.community.exception;

public class CustomizedException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomizedException(ICustomizedErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
