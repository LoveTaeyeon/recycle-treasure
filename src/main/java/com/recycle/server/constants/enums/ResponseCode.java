package com.recycle.server.constants.enums;

public enum ResponseCode {

    UNKNOWN_EXCEPTION(9999, "Something went wrong ..."),
    OK(10000, "Success"),
    INVALID_REQUEST(10001, "Invalid Request");

    private Integer code;
    private String message;

    private ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
