package com.mx.server.framework.error;

public enum EmBusinessErr implements CommonError {
    //常规状态错误
    PARAMETER_INVALIDATION(1000, "参数不合法"),
    UNKNOWN_ERROR(1001, "未知错误"),
    AUTHORIZATION_DENIED(1002, "无权限"),

    //登录
    CAPTCHA_ERROR(1003, "验证码生成错误"),
    CAPTCHA_NOT_EXISTED(1004, "验证码不存在"),
    CAPTCHA_TIME_OUT(1005, "验证码超时"),
    CAPTCHA_VERIFY_ERROR(1006, "验证码错误"),
    NOT_LOGIN(1007, "未登录"),

    // 账号操作错误
    ACCOUNT_NOT_EXISTED(2000, "账号不存在"),
    LOGIN_ACCOUNT_PASSWORD_ERROR(2001, "账号或密码错误"),
    LOGIN_ERROR(2003, "登录失败"),
    ACCOUNT_EXISTED(2004, "账号已存在"),

    //文件
    FILE_EXISTED(3000, "文件已上传"),

    FILE_UPLOAD_ERROR(3001, "文件上传失败");


    private final Integer errCode;
    private String msg;

    EmBusinessErr(Integer errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.errCode;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
    }
