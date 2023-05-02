package com.mx.server.util.password;

import com.mx.server.util.RandomUtil;
import com.mx.server.util.hash.SHAUtil;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maoxian
 */
public final class PasswordSHA implements IPasswordCalculator {
    private static PasswordSHA instance = null;

    private PasswordSHA() {
    }

    public static PasswordSHA getInstance() {
        if (instance == null) {
            synchronized (PasswordSHA.class) {
                instance = new PasswordSHA();
            }
        }
        return instance;
    }

    private final String VERSION = "1.0";
    private final PasswordEnum TYPE = PasswordEnum.SHA;
    private final int ROUND = 17777;
    private final int SALT_LEN = 64;
    private final int PICK = 17;

    class VersionInfo {
        PasswordEnum type;
        int round;
        int saltLen;
        int pick;

        VersionInfo(PasswordEnum type, int round, int saltLen, int pick) {
            this.type = type;
            this.round = round;
            this.saltLen = saltLen;
            this.pick = pick;
        }
    }

    private final Map<String, VersionInfo> VERSION_INFO;

    {
        VERSION_INFO = new HashMap<>();
        // 旧版本 (更新版本后, 将原版本数据移至此处, 仅用于兼容)

        // 现版本 (方法使用最新版本)
        VERSION_INFO.put(VERSION, new VersionInfo(TYPE, ROUND, SALT_LEN, PICK));
    }

    @Override
    public Password getPassword(String src) {
        String salt = RandomUtil.salt(SALT_LEN);
        return getPassword(VERSION, TYPE, src, salt, ROUND, PICK);
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
        return getPassword(password.getVersion(),
                versionInfo.type,
                password.getCode(),
                password.getSalt(),
                versionInfo.round,
                versionInfo.pick);
    }

    private Password getPassword(String version, PasswordEnum type, String src, String salt, int round, int pick) {
        Password password = new Password(version, type);
        String fore = salt.substring(0, pick);
        String back = salt.substring(pick);
        String code = fore.concat(src).concat(back);
        for (int i = 0; i < round; i++) {
            code = SHAUtil.SHA256(code);
        }
        password.setCode(code);
        password.setSalt(salt);
        return password;
    }
}
