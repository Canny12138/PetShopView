package com.soft.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Project name:petShop
 * Author: NoFat
 * Create time:2022/6/22 8:34
 **/
public class Md5Util {
    private static String md5Encode(String str) throws Exception{
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            return "";
        }
        byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    public static String getEncode(String pwd) throws Exception{
        return md5Encode(md5Encode(pwd));
    }

    public static void main(String[] args) throws Exception {
        String code = "";
        System.out.println(Md5Util.getEncode(code));
    }
}
