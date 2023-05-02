package com.mx.server.util;

import java.security.InvalidParameterException;
import java.util.Random;

/**
 * @author Maoxian
 * #date 2019/05/28 21:06
 */
public final class RandomUtil {
    private final static char[][] CHARS = {
            // "abcdefghijklmnopqrstuvwxyz".toCharArray(),
            // "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(),
            // "1234567890".toCharArray(),
            // "~;<@#:>%^".toCharArray()

            // 小写(提前乱序)
            "ulpcewbykhmoajtxsiqzngvdfr".toCharArray(),
            // 大写
            "RYBWHIOQPFCKZMEGLXDUSATNVJ".toCharArray(),
            "4862730195".toCharArray(),
            "~;<@#:>%^".toCharArray()
    };

    /**
     * 获取随机字符串
     * len 的范围 [4,1024]
     */
    public static String key(int len) {
        Random rand = new Random();

        if (len < 4 || len > 1024) {
            throw new InvalidParameterException("len 不在 [4,1024] 中");
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < len) {
            int i = rand.nextInt(CHARS.length);
            int j = rand.nextInt(CHARS[i].length);
            sb.append(CHARS[i][j]);
        }
        shuffle(sb);
        return sb.toString();
    }

    public static String keySmallChar(int len){
        StringBuilder sb = new StringBuilder();
        while (sb.length()<len){
            sb.append(CHARS[0][nextInt(0, CHARS[0].length)]);
        }
        shuffle(sb);
        return sb.toString();
    }

    /**
     * 获取盐
     * 保证含有各类字符的随机字符串
     * len 的范围 [8,1024]
     */
    public static String salt(int len) {
        Random rand = new Random();

        if (len < 8 || len > 1024) {
            throw new InvalidParameterException("len 不在 [8,1024] 中");
        }
        StringBuilder sb = new StringBuilder();
        int eachLen = len / 4;
        for (char[] chars : CHARS) {
            for (int i = 0; i < eachLen; i++) {
                sb.append(chars[rand.nextInt(chars.length)]);
            }
        }
        while (sb.length() < len) {
            int i = rand.nextInt(CHARS.length);
            int j = rand.nextInt(CHARS[i].length);
            sb.append(CHARS[i][j]);
        }
        sb.substring(0, len);
        shuffle(sb);
        return sb.toString();
    }

    public static String shuffle(String src) {
        return shuffle(new StringBuilder(src)).toString();
    }

    public static StringBuilder shuffle(StringBuilder src) {
        if (src.length() <= 1) {
            return new StringBuilder(src);
        }
        for (int i = 0; i < src.length() - 1; i++) {
            int pos = nextInt(i, src.length() - 1, true);
            if (pos != i) {
                char ch = src.charAt(pos);
                src.setCharAt(pos, src.charAt(i));
                src.setCharAt(i, ch);
            }
        }
        return src;
    }

    /**
     * @author Maoxian
     * 获取范围内的int数值，左闭右开 [min, max)
     */
    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new InvalidParameterException("参数范围错误");
        }
        Random rand = new Random();

        return rand.nextInt(max - min) + min;
    }

    /**
     * @author Maoxian
     * 获取范围内的int数值，equal表示是否取端点值
     */
    public static int nextInt(int min, int max, boolean equal) {
        if (min >= max) {
            throw new InvalidParameterException("参数范围错误");
        }
        Random rand = new Random();
        if (equal) {
            return rand.nextInt(max - min + 1) + min;
        } else {
            return rand.nextInt(max - min - 1) + min + 1;
        }
    }

    /**
     * 生成低并发下的随机id，时间戳+6位随机码
     */
    public static String genRanId(Object... args) {
        long now = System.currentTimeMillis();
        return Long.toHexString(now) + "-" + keySmallChar(6);
    }
}
