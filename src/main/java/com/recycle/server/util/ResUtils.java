package com.recycle.server.util;

import com.recycle.server.constants.enums.ResponseCode;
import com.recycle.server.entity.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResUtils {

    public static ResponseEntity unknownException(Exception e) {
        return new ResponseEntity<>(
                GeneralResponse.error(ResponseCode.UNKNOWN_EXCEPTION, e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    public static ResponseEntity invalidReq(String message) {
        return new ResponseEntity<>(
                GeneralResponse.error(ResponseCode.INVALID_REQUEST, message),
                HttpStatus.BAD_REQUEST
        );
    }

    public static ResponseEntity ok() {
        return new ResponseEntity<>(GeneralResponse.OK, HttpStatus.OK);
    }

    public static ResponseEntity ok(Object data) {
        return new ResponseEntity<>(GeneralResponse.ok(data), HttpStatus.OK);
    }

}
