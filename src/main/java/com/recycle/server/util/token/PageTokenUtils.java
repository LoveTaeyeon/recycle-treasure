package com.recycle.server.util.token;

import com.alibaba.fastjson.JSON;
import com.recycle.server.constants.PagingType;
import com.recycle.server.entity.model.PageToken;

import java.util.Base64;

public class PageTokenUtils {

    public static PageToken parse(String pageToken, PagingType type) throws Exception {
        String str = new String(Base64.getDecoder().decode(pageToken));
        PageToken token = JSON.parseObject(str, PageToken.class);
        if (token.getType() == null || !token.getType().equals(type.getCode())) {
            throw new Exception("Invalid pageToken !");
        }
        return token;
    }

    public static String build(PageToken token) {
        return new String(Base64.getEncoder().encode(JSON.toJSONString(token).getBytes()));
    }

}
