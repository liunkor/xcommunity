package com.community.exception;

public enum CustomizedErrorCode implements ICustomizedErrorCode{
    QUESTION_NOT_FOUND(2001, "The question not exists."),
    TARGET_PARAM_NOT_FOUND(2002, "No question or comment be selected."),
    NO_LOGIN(2003, "You are not logined in. Please login in first."),
    SYS_ERROR(2004, "Oh~~ some errors with server. Please try later"),
    TYPE_PARAM_WRONG(2005, "Comment type not exists."),
    COMMENT_NOT_FOUND(2006, "Comment not found."),
    COMMENT_IS_EMPTY(2007, "Comment content can't be blank");


    private Integer code;
    private final String message;

    CustomizedErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
