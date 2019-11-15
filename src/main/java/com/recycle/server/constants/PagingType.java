package com.recycle.server.constants;

public enum PagingType {

    OFFSET("OFFSET", 0);

    private String pagingType;
    private Integer code;

    private PagingType(String pagingType, Integer code) {
        this.pagingType = pagingType;
        this.code = code;
    }

    public String getPagingType() {
        return pagingType;
    }

    public Integer getCode() {
        return code;
    }

    public static PagingType getByCode(Integer code) throws Exception {
        for (PagingType type : PagingType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new Exception("[Unknown Paging Code] code " + code);
    }

}
