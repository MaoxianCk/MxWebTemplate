package com.mx.server.framework.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mx.server.framework.components.RedisCache;
import com.mx.server.framework.constant.RedisConstants;
import com.mx.server.framework.error.BusinessException;
import com.mx.server.framework.error.EmBusinessErr;
import com.mx.server.framework.service.CommonService;
import com.mx.server.util.ContextUtil;
import com.mx.server.util.captcha.CaptchaData;
import com.mx.server.util.captcha.ImageCaptchaUtil;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Maoxian
 * @since 2022/5/22
 */
@Service
public class CommonServiceImpl implements CommonService {
    private final RedisCache redisCache;

    /**
     * 验证码Header key值
     */
    private static final String CAPTCHA_KEY = "CAPTCHA_CPK";

    /**
     * 验证码Header 截止时间 key值
     */
    private static final String CAPTCHA_TIME_KEY = "CAPTCHA_CPT";

    /**
     * 验证码 redis key
     */
    private static final String CAPTCHA_REDIS_KEY = RedisConstants.CAPTCHA_PREFIX + CAPTCHA_KEY + ":";

    /**
     * 验证码有效期 默认60s
     */
    @Value("${captcha.expire: 60}")
    private int captchaExpire;

    public CommonServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 生成图形验证码
     * 将code保存到redis中
     * 将图片塞到response里
     * 校验信息放在header中
     */
    public void getImgCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(ImageCaptchaUtil.getWidth(), ImageCaptchaUtil.getHeight(), 5);
        specCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);

        // 生成验证码信息
        CaptchaData captchaData = new CaptchaData();
        captchaData.setCode(specCaptcha.text());
        captchaData.setKey(IdUtil.fastSimpleUUID());
        captchaData.setHeaderKey(CAPTCHA_KEY);
        captchaData.setHeaderTimeKey(CAPTCHA_TIME_KEY);

        // redis储存验证码, 加10秒延迟时间
        redisCache.setCacheObject(CAPTCHA_REDIS_KEY + captchaData.getKey(), captchaData.getCode(), captchaExpire + 10, TimeUnit.SECONDS);

        // 设置过期时间
        captchaData.setExpireTime(System.currentTimeMillis() + (captchaExpire * 1000L));
        // 将验证码等信息设置到response中
        ImageCaptchaUtil.setCaptchaImgToResponse(ContextUtil.getResponse(), specCaptcha, captchaData);
    }

    public void verifyImgCaptcha(String code) throws BusinessException {
        String key = ContextUtil.getFromHeader(CAPTCHA_KEY);
        // String end = ContextUtil.getFromHeader(CAPTCHA_TIME_KEY);

        // 验证码不存在
        if (StrUtil.hasBlank(key)) {
            throw new BusinessException(EmBusinessErr.CAPTCHA_ERROR, "验证码错误");
        }

        // 超时
        // Long endL = Convert.toLong(end, null);
        // if (endL == null || System.currentTimeMillis() >= endL) {
        //     throw new BusinessException(EmBusinessErr.CAPTCHA_TIME_OUT);
        // }

        String qKey = CAPTCHA_REDIS_KEY + key;
        // 取不到 即超时
        String srcCode = redisCache.getCacheObject(qKey);
        if (srcCode == null) {
            throw new BusinessException(EmBusinessErr.CAPTCHA_TIME_OUT);
        }

        // 查完就删
        redisCache.deleteObject(qKey);

        // 校验
        if (!srcCode.equalsIgnoreCase(code)) {
            throw new BusinessException(EmBusinessErr.CAPTCHA_VERIFY_ERROR);
        }
    }
}
