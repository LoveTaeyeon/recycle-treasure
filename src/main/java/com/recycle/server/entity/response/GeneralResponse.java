package com.recycle.server.entity.response;

import com.recycle.server.constants.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {

    private Object data;
    private Integer code;
    private String message;

    public static GeneralResponse OK = GeneralResponse.builder()
            .code(ResponseCode.OK.getCode())
            .message(ResponseCode.OK.getMessage())
            .build();

    public static GeneralResponse ok(Object data) {
        return GeneralResponse.builder()
                .data(data)
                .code(ResponseCode.OK.getCode())
                .message(ResponseCode.OK.getMessage())
                .build();
    }

    public static GeneralResponse error(ResponseCode code) {
        return GeneralResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static GeneralResponse error(ResponseCode code, Object message) {
        return GeneralResponse.builder()
                .data(message)
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

}
