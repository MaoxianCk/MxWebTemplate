package com.mx.server.framework.service;

/**
 * @author Maoxian
 * @since 2022/5/22
 * 通用功能模块
 */
public interface CommonService {
    void getImgCaptcha();

    void verifyImgCaptcha(String code);
}
