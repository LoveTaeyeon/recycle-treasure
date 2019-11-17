package com.recycle.server.util.token;

import com.recycle.server.entity.User;
import com.recycle.server.util.AESUtils;

public class TokenUtils {

    private static final String COMMA = ",";

    private static String buildString(Integer id, String token) {
        return id + COMMA + token;
    }

    public static String build(User user) throws Exception {
        return AESUtils.encrypt(buildString(user.getId(), user.getToken()));
    }

    public static String build(Integer id, String token) throws Exception {
        return AESUtils.encrypt(buildString(id, token));
    }

    public static boolean validate(User user, String token) throws Exception {
        String value = AESUtils.decrypt(token);
        String valueArr[] = value.split(COMMA);
        return user.getId().equals(Integer.valueOf(valueArr[0]))
                && user.getToken().equals(valueArr[1]);
    }

    public static boolean validate(Integer id, String latestToken, String token) throws Exception {
        String value = AESUtils.decrypt(token);
        String valueArr[] = value.split(COMMA);
        return id.equals(Integer.valueOf(valueArr[0]))
                && latestToken.equals(valueArr[1]);
    }

}
