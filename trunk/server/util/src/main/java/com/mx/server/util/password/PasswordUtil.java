package com.mx.server.util.password;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maoxian
 * #date 2019/05/28 21:35
 */
public final class PasswordUtil {
    private final String VERSION = "1.0";
    private static final Map<PasswordEnum, IPasswordCalculator> PASSWORD_CALCULATOR_MAP;

    static {
        PASSWORD_CALCULATOR_MAP = new HashMap<>();
        PASSWORD_CALCULATOR_MAP.put(PasswordEnum.SHA, PasswordSHA.getInstance());
        PASSWORD_CALCULATOR_MAP.put(PasswordEnum.PBKDF2, PasswordPBKDF2.getInstance());
        PASSWORD_CALCULATOR_MAP.put(PasswordEnum.B_CRYPT, PasswordBCrypt.getInstance());
    }

    private static IPasswordCalculator getPasswordCalculator(PasswordEnum type) {
        return PASSWORD_CALCULATOR_MAP.get(type);
    }

    /**
     * @param psw 用户输入密码原文
     * @author Maoxian
     * 根据选择的算法类型对密码进行处理, 默认使用BCrypt
     */
    public static String getPassword(String psw) {
        return getPassword(psw, PasswordEnum.B_CRYPT);
    }

    /**
     * @param psw  用户输入密码原文
     * @param type 加密算法
     * @author Maoxian
     * 根据选择的算法类型对密码进行处理
     */
    public static String getPassword(String psw, PasswordEnum type) {
        return getPasswordCalculator(type).getPassword(psw).encode();
    }

    /**
     * @param psw  用户输入原文
     * @param code 数据库存储的code编码
     * @author Maoxian
     * 验证密码是否正确
     */
    public static boolean verifyPassword(String psw, String code) {
        Password codePwd = Password.decode(code);
        return getPasswordCalculator(codePwd.getType()).verifyPassword(psw, codePwd);
    }
}
