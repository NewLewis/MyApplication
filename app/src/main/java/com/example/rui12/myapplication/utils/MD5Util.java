package com.example.rui12.myapplication.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private final static String AUTH = "帐号加密串";

    private byte[] encode2bytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    //将源字符串使用MD5加密为32位16进制数
    public String encode2hex(String source) {
        byte[] data = encode2bytes(source);

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * 验证字符串是否匹配
     *
     * @param unknown 待验证的字符串
     * @return 匹配返回true，不匹配返回false
     */
    public boolean validate(String unknown) {
        return AUTH.equals(encode2hex(unknown));
    }

}