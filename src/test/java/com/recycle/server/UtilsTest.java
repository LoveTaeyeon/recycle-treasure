package com.recycle.server;

import com.recycle.server.constants.PagingType;
import com.recycle.server.entity.model.PageToken;
import com.recycle.server.util.AESUtils;
import com.recycle.server.util.token.PageTokenUtils;
import com.recycle.server.util.token.TokenUtils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void testPageTokenUtil() throws Exception {
        PageToken token = PageToken.builder()
                .nextId(1000L)
                .preId(900L)
                .type(PagingType.OFFSET.getCode())
                .build();
        PageToken parsedToken = PageTokenUtils.build(token);
        token = PageTokenUtils.parse(parsedToken.getNextPageToken(), PagingType.OFFSET);
        Assert.assertEquals(token.getNextId().longValue(), 1000L);
        Assert.assertEquals(token.getPreId().longValue(), 900L);

        token.setType(null);
        parsedToken = PageTokenUtils.build(token);

        int errorCount = 0;
        try {
            PageTokenUtils.parse(parsedToken.getNextPageToken(), PagingType.OFFSET);
        } catch (Exception e) {
            errorCount++;
        }
        Assert.assertEquals(errorCount, 1);
    }

    @Test
    public void testAESUtils() throws Exception {
        String value = "123";
        String aes = AESUtils.encrypt(value);
        Assert.assertEquals(value, AESUtils.decrypt(aes));
    }

    @Test
    public void testAccessTokenUtils() throws Exception {
        String token = TokenUtils.build(100, "weiXinSessionToken");
        boolean r1 = TokenUtils.validate(100, "test", token);
        boolean r2 = TokenUtils.validate(101, "weiXinSessionToken", token);
        boolean r3 = TokenUtils.validate(100, "weiXinSessionToken", token);
        Assert.assertFalse(r1);
        Assert.assertFalse(r2);
        Assert.assertTrue(r3);
    }

}
