package com.mx.server.util.password;

/**
 * @author Maoxian
 */
public interface IPasswordCalculator {
    Password getPassword(String src);

    /**
     * @author Maoxian
     * 内code为密码源文
     * 返回后code为密文
     */
    Password getPassword(Password password);

    default boolean verifyPassword(String plain, Password hashed) {
        Password plainPwd = new Password(hashed.getVersion(), hashed.getType());
        plainPwd.setCode(plain);
        plainPwd.setSalt(hashed.getSalt());
        return getPassword(plainPwd).getCode().equals(hashed.getCode());
    }
}