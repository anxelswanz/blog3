package com.ansel.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Ansel Zhong
 * @title ajaxPlay
 * @Date 2023/1/19
 * @Description MD5 Encoding
 */
public class MD5Utils {
    private static final String SALT = "1a2b3c";

    public static String pass2(String password){
        String str = "" + SALT.charAt(0) + password +SALT.charAt(5);
        String str2 = md5(str);
        String str3 = pass1(str2);
        return str3;
    }

    public static String pass1(String password) {
        String str = "" + SALT.charAt(0) + password +SALT.charAt(5);
        return md5(str);
    }

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
}
