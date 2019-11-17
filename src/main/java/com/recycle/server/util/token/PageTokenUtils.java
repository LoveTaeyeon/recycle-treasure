package com.recycle.server.util.token;

import com.alibaba.fastjson.JSON;
import com.recycle.server.constants.PagingType;
import com.recycle.server.entity.model.PageToken;

import java.util.Base64;

public class PageTokenUtils {

    public static PageToken parse(String pageToken, PagingType type) throws Exception {
        if (pageToken == null) {
            return PageToken.empty();
        }
        String str = new String(Base64.getDecoder().decode(pageToken));
        PageToken token = JSON.parseObject(str, PageToken.class);
        if (token.getType() == null || !token.getType().equals(type.getCode())) {
            throw new Exception("Invalid pageToken !");
        }
        return token;
    }

    private static String buildTokenString(PageToken token) {
        return new String(Base64.getEncoder().encode(JSON.toJSONString(token).getBytes()));
    }

    public static PageToken build(PageToken token) {
        if (token == null) {
            return null;
        }
        String nextPageToken = buildTokenString(token);
        token.setNextId(token.getPreId());
        String prePageToken = buildTokenString(token);
        return PageToken.builder().nextPageToken(nextPageToken).prePageToken(prePageToken).build();
    }

}
