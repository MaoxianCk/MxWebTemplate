package com.mx.server.util.password;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Base64;

/**
 * @author Maoxian
 * 密码实体封装类
 */
public class Password {
    /**
     * 使用的算法版本号
     */
    private String version;

    /**
     * 使用的算法
     */
    private PasswordEnum type;

    /**
     * 密码或秘文
     */
    private String code;

    /**
     * 混淆
     */
    private String salt;

    /**
     * 分隔符
     */
    private static final String SEPARATOR = "|";

    public Password() {
    }

    public Password(String version, PasswordEnum type) {
        this.version = version;
        this.type = type;
    }

    public Password(String version, PasswordEnum type, String code, String salt) {
        this.version = version;
        this.type = type;
        this.code = code;
        this.salt = salt;
    }

    /**
     * 解码
     * 必须和encode配套
     */
    public static Password decode(String code) {
        Password password = new Password();
        // 根据分隔符进行拆分
        String[] res = code.split("\\" + SEPARATOR);
        // 拆分的结果必须为4类
        if (res.length == 4) {
                password.setVersion(decodeBase64(res[0]));
                password.setType(PasswordEnum.valueOf(decodeBase64(res[1])));
                password.setCode(decodeBase64(res[2]));
                password.setSalt(decodeBase64(res[3]));
                return password;
        } else {
            throw new InvalidParameterException("密码解码错误: 格式错误");
        }
    }

    /**
     * 编码，将密码本信息编码成单字符串
     * 必须和decode配套
     */
    public String encode()   {
        //
        if (this.version == null ||
                this.type == null ||
                this.code == null ||
                this.salt == null) {
            throw new InvalidParameterException("密码编码错误: 数据错误");
        }

        return encodeBase64(this.version) +
                SEPARATOR +
                encodeBase64(this.type.name()) +
                SEPARATOR +
                encodeBase64(this.code) +
                SEPARATOR +
                encodeBase64(this.salt);
    }

    /**
     * 解码base64辅助函数
     */
    private static String decodeBase64(String base64Code) {
        return new String(Base64.getDecoder().decode(base64Code), StandardCharsets.UTF_8);
    }

    /**
     * 编码base64辅助函数
     */
    private static String encodeBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 检验两个密码的内部编码是否相同
     */
    public boolean equalPassword(Password password) {
        return this.code.equals(password.code);
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PasswordEnum getType() {
        return type;
    }

    public void setType(PasswordEnum type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
