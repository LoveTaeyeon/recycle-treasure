package com.recycle.server.entity.exception;

import com.recycle.server.constants.ResponseStrings;

public class InvalidSession extends Exception {

    public InvalidSession() {
        super(ResponseStrings.INVALID_SESSION);
    }

    public InvalidSession(String message) {
        super(message);
    }

}
