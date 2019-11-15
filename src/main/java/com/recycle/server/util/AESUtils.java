package com.recycle.server.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static String KEY = "1234!@#$qwerQWER";
    private static String CHARSET = "utf-8";
    private static int OFFSET = 16;
    private static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static String ALGORITHM = "AES";

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY);
    }

    public static String decrypt(String content) throws Exception {
        return decrypt(content, KEY);
    }

    private static String encrypt(String content, String key) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] byteContent = content.getBytes(CHARSET);
        cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
        byte[] result = cipher.doFinal(byteContent);
        return new Base64().encodeToString(result);
    }

    private static String decrypt(String content, String key) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, skey, iv);
        byte[] result = cipher.doFinal(new Base64().decode(content));
        return new String(result);
    }

}
