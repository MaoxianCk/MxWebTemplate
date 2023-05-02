package com.mx.server.util.password;

import org.mindrot.jbcrypt.BCrypt;
import com.mx.server.util.RandomUtil;

/**
 * @author Maoxian
 */
public class PasswordBCrypt implements IPasswordCalculator {

    private static PasswordBCrypt instance = null;

    private PasswordBCrypt() {
    }

    public static PasswordBCrypt getInstance() {
        if (instance == null) {
            synchronized (PasswordBCrypt.class) {
                instance = new PasswordBCrypt();
            }
        }
        return instance;
    }

    private final String VERSION = "1.0";
    private final PasswordEnum TYPE = PasswordEnum.B_CRYPT;
    private final int SALT_LEN = 8;

    @Override
    public Password getPassword(String src) {
        Password password = new Password(VERSION, TYPE);
        password.setCode(src);
        return getPassword(password);
    }

    @Override
    public Password getPassword(Password password) {
        password.setCode(BCrypt.hashpw(password.getCode(), BCrypt.gensalt()));
        // 并没有用
        password.setSalt(RandomUtil.salt(SALT_LEN));
        return password;
    }

    @Override
    public boolean verifyPassword(String plain, Password hashed) {
        return BCrypt.checkpw(plain, hashed.getCode());
    }
}
