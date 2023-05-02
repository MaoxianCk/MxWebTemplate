package com.mx.server.util.password;

/**
 * @author Maoxian
 * 密码类型枚举
 */
public enum PasswordEnum {
    /**
     * PBKDF2 算法
     */
    PBKDF2,
    /**
     * B_CRYPT 算法，慢哈希，默认
     */
    B_CRYPT,
    /**
     * SHA 算法，最简易，最快
     */
    SHA
}
