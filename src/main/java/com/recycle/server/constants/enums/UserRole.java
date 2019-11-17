package com.recycle.server.constants.enums;

public enum UserRole {
    ADMIN(0, "ADMIN");

    private Integer code;
    private String description;

    private UserRole(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
