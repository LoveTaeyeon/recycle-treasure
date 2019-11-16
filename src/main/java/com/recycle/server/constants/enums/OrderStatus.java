package com.recycle.server.constants.enums;

public enum OrderStatus {
    WAITING(0, "待处理"),
    PROCESSING(1, "处理中"),
    CANCEL(2, "取消"),
    DELETE(3, "已删除"),
    DONE(4, "完成");

    private Integer code;
    private String description;

    private OrderStatus(Integer code, String description) {
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
