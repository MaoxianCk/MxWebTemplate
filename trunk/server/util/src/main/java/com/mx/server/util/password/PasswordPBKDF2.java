package com.mx.server.util.password;

import com.mx.server.util.RandomUtil;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;

import static com.mx.server.util.ConvertUtil.bytesToHex;

/**
 * @author Maoxian
 */
public final class PasswordPBKDF2 implements IPasswordCalculator {

    private static PasswordPBKDF2 instance = null;

    private PasswordPBKDF2() {
    }

    public static PasswordPBKDF2 getInstance() {
        if (instance == null) {
            synchronized (PasswordPBKDF2.class) {
                instance = new PasswordPBKDF2();
            }
        }
        return instance;
    }

    private final String VERSION = "1.0";
    private final PasswordEnum TYPE = PasswordEnum.PBKDF2;
    private final int ITERATIONS = 10000;
    private final int SALT_LEN = 32;
    private final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
    private final int DK_LEN = 32;

    class VersionInfo {
        PasswordEnum type;
        int iterations;
        int saltLen;
        String hashAlgorithm;
        int dkLen;

        public VersionInfo(PasswordEnum type, int iterations, int saltLen, String hashAlgorithm, int dkLen) {
            this.type = type;
            this.iterations = iterations;
            this.saltLen = saltLen;
            this.hashAlgorithm = hashAlgorithm;
            this.dkLen = dkLen;
        }
    }

    private final Map<String, VersionInfo> VERSION_INFO;

    {
        VERSION_INFO = new HashMap<>();
        // 旧版本 (更新版本后, 将原版本数据移至此处, 仅用于兼容)

        // 现版本 (方法使用最新版本)
        VERSION_INFO.put(VERSION, new VersionInfo(TYPE, ITERATIONS, SALT_LEN, HASH_ALGORITHM, DK_LEN));
    }

    private String pbkdf2(String password, String salt, int iterations, String hashAlgorithm, int dkLen) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(hashAlgorithm);
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, dkLen * 8);
            SecretKey secret = keyFactory.generateSecret(keySpec);
            byte[] rawHash = secret.getEncoded();
            return bytesToHex(rawHash);
        } catch (Exception e) {
            throw new InvalidParameterException("加密错误");
        }
    }

    @Override
    public Password getPassword(String src) {
        Password password = new Password(VERSION, TYPE);
        password.setSalt(RandomUtil.salt(SALT_LEN));
        password.setCode(src);
        return getPassword(password);
    }

    /**
     * @author Maoxian
     * 内code为密码源文
     * 返回后code为密文
     */
    @Override
    public Password getPassword(Password password) {
        if (password.getType() != TYPE) {
            throw new InvalidParameterException("密码类型错误");
        }

        if (!VERSION_INFO.containsKey(password.getVersion())) {
            throw new InvalidParameterException("密码版本错误");
        }

        VersionInfo versionInfo = VERSION_INFO.get(password.getVersion());

        password.setCode(
                pbkdf2(
                        password.getCode(),
                        password.getSalt(),
                        versionInfo.iterations,
                        versionInfo.hashAlgorithm,
                        versionInfo.dkLen)
        );

        return password;
    }
}
