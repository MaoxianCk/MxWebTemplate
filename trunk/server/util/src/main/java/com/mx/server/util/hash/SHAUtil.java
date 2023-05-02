package com.mx.server.util.hash;

import com.mx.server.util.ConvertUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * SHA 加密
 */
@SuppressWarnings("ALL")
public class SHAUtil {
    /**
     * SHA1 加密 生成40位16进制密文字符串
     */
    public static String SHA1(String str)   {
        return SHABase(str, "SHA-1");
    }

    /**
     * SHA256 加密 生成64位16进制密文字符串
     */
    public static String SHA256(String str)   {
        return SHABase(str, "SHA-256");
    }

    /**
     * SHA256 加密 生成128位16进制密文字符串
     */
    public static String SHA512(String str)   {
        return SHABase(str, "SHA-512");
    }

    private static String SHABase(String str, String digit)   {
        if (str == null || str.length() == 0) {
            return "";
        }
        try {
            MessageDigest sha = MessageDigest.getInstance(digit);
            byte[] messageDigest = sha.digest(str.getBytes(StandardCharsets.UTF_8));
            return ConvertUtil.bytesToHex(messageDigest);
        } catch (Exception e) {
            return null;
        }
    }
}
