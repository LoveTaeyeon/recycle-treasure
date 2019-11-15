package com.recycle.server.util;

import java.sql.Timestamp;

public class DateExtUtils {

    public static long currentTimeMill() {
        return System.currentTimeMillis();
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
